package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PatientRequest;
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

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    @Autowired
    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper, RequestValidator validator) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public List<Patient> getPatients(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<PatientEntity> patients = patientRepository.findAll(pageable).getContent();

        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }

    public Patient createPatient(PatientRequest patientRequest) {
        LocalDateTime now = LocalDateTime.now();
        PatientEntity patientEntity = PatientEntity.builder()
                .name(patientRequest.getName())
                .surname(patientRequest.getSurname())
                .email(patientRequest.getEmail())
                .pesel(patientRequest.getPesel())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        PatientEntity savedPatientEntity = patientRepository.save(patientEntity);

        return modelMapper.mapPatientEntityToPatient(savedPatientEntity);
    }

    public Patient getPatientById(Long id) {
        PatientEntity patientEntity = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));

        return modelMapper.mapPatientEntityToPatient(patientEntity);
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        validator.validateDoctor(doctorId);

        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientEntity> patients = patientRepository.findPatientsByDoctorId(doctorId, pageRequest).getContent();

        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }

    public List<Patient> getUnassignedPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        validator.validateDoctor(doctorId);

        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientEntity> patients = patientRepository.findUnassignedPatientsByDoctorId(doctorId, pageRequest).getContent();

        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }
}
