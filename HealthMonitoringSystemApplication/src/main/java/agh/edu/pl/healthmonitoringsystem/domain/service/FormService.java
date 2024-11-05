package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.FormRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Form;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.FormEntryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.FormRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final FormEntryRepository formEntryRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public FormService(FormRepository formRepository, FormEntryRepository formEntryRepository, RequestValidator validator,
                       ModelMapper modelMapper) {
        this.formRepository = formRepository;
        this.formEntryRepository = formEntryRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
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
        Pageable pageable = PageRequest.of(page, size);

        List<FormEntity> formEntities = formRepository.findByPatientId(patientId, pageable);

        return formEntities.stream()
                .map(formEntity -> {
                    List<FormEntryEntity> entries = formEntryRepository.findByFormId(formEntity.getId());
                    return modelMapper.mapFormEntityToForm(formEntity, entries);
                })
                .toList();
    }

    public Form getPatientLatestHealthForm(Long patientId) {
        FormEntity latestFormEntity = formRepository.findTopByPatientIdOrderByCreatedDateDesc(patientId);
        List<FormEntryEntity> entries = formEntryRepository.findByFormId(latestFormEntity.getId());

        return modelMapper.mapFormEntityToForm(latestFormEntity, entries);
    }
}