package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public List<Patient> getPatients(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
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
}
