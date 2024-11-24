package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.FormRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.FormEntryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.FormRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final FormEntryRepository formEntryRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public FormService(FormRepository formRepository, UserRepository userRepository, FormEntryRepository formEntryRepository, RequestValidator validator,
                       ModelMapper modelMapper) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
        this.formEntryRepository = formEntryRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public Form getFormById(Long formId) {
        FormEntity formEntity = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form with id " + formId + " not found"));

        List<FormEntryEntity> formEntries = formEntryRepository.findByFormId(formEntity.getId());

        return modelMapper.mapFormEntityToForm(formEntity, formEntries);
    }

    public Form uploadHealthForm(FormRequest formRequest) {
        validator.validatePatient(formRequest.getPatientId());

        LocalDateTime now = LocalDateTime.now();
        FormEntity formEntity = FormEntity.builder()
                .patientId(formRequest.getPatientId())
                .createdDate(now)
                .build();
        FormEntity savedFormEntity = formRepository.save(formEntity);

        List<FormEntryEntity> formEntryEntities = formRequest.getContent().stream()
                .map(entry -> FormEntryEntity.builder()
                        .formId(savedFormEntity.getId())
                        .value(entry.getValue())
                        .healthParam(entry.getKey())
                        .build())
                .toList();

        formEntryRepository.saveAll(formEntryEntities);

        return modelMapper.mapFormEntityToForm(savedFormEntity, formEntryEntities);
    }

    public List<Form> getPatientHealthForms(Long patientId, Integer page, Integer size) {
        validator.validatePatient(patientId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<FormEntity> formEntities = formRepository.findByPatientId(patientId, pageable);

        return formEntities.stream()
                .map(formEntity -> {
                    List<FormEntryEntity> formEntries = formEntryRepository.findByFormId(formEntity.getId());
                    return modelMapper.mapFormEntityToForm(formEntity, formEntries);
                })
                .toList();
    }

    public List<Form> getAllHealthForms(Long userId, Long patientId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));

        Page<FormEntity> formEntities = (patientId == null)
                ? fetchFormsForUserRole(user, pageable)
                : fetchFormsForPatient(user, patientId, pageable);

        return formEntities.stream()
                .map(this::mapFormWithEntries)
                .toList();
    }

    private Page<FormEntity> fetchFormsForUserRole(UserEntity user, Pageable pageable) {
        if (user.getRole().equals(Role.PATIENT)) {
            return formRepository.findByPatientId(user.getId(), pageable);
        }
        return formRepository.findAll(pageable);
    }

    private Page<FormEntity> fetchFormsForPatient(UserEntity user, Long patientId, Pageable pageable) {
        validator.validateAccessToPatientData(user, patientId);
        return formRepository.findByPatientId(patientId, pageable);
    }

    private Form mapFormWithEntries(FormEntity formEntity) {
        List<FormEntryEntity> formEntries = formEntryRepository.findByFormId(formEntity.getId());
        return modelMapper.mapFormEntityToForm(formEntity, formEntries);
    }
}