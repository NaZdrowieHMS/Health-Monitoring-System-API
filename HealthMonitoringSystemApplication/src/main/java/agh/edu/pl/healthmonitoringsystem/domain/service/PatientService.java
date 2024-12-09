package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PatientRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    @Autowired
    public PatientService(UserRepository userRepository, ModelMapper modelMapper, RequestValidator validator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public List<Patient> getPatients(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<UserEntity> patients = userRepository.findAllPatients(pageable).getContent();

        return patients.stream()
                .map(modelMapper::mapUserEntityToPatient)
                .collect(Collectors.toList());
    }

    public Patient createPatient(PatientRequest patientRequest) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity patientEntity = UserEntity.builder()
                .role(Role.PATIENT)
                .name(patientRequest.getName())
                .surname(patientRequest.getSurname())
                .email(patientRequest.getEmail())
                .pesel(patientRequest.getPesel())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        UserEntity savedPatientEntity = userRepository.save(patientEntity);

        return modelMapper.mapUserEntityToPatient(savedPatientEntity);
    }

    public Patient getPatientById(Long id) {
        UserEntity patientEntity = userRepository.findPatientById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));

        return modelMapper.mapUserEntityToPatient(patientEntity);
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        validator.validateDoctor(doctorId);

        PageRequest pageRequest = PageRequest.of(page, size);
        List<UserEntity> patients = userRepository.findPatientsByDoctorId(doctorId, Role.PATIENT, pageRequest).getContent();

        return patients.stream()
                .map(modelMapper::mapUserEntityToPatient)
                .collect(Collectors.toList());
    }

    public List<Patient> getUnassignedPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        validator.validateDoctor(doctorId);

        PageRequest pageRequest = PageRequest.of(page, size);
        List<UserEntity> patients = userRepository.findUnassignedPatientsByDoctorId(doctorId, Role.PATIENT, pageRequest).getContent();

        return patients.stream()
                .map(modelMapper::mapUserEntityToPatient)
                .collect(Collectors.toList());
    }
}
