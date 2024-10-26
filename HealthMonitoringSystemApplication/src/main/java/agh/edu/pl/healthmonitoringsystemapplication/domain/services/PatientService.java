package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patientPage = patientRepository.findAll(pageable);
        return patientPage.getContent();
    }

    public Patient createPatient(PatientRequest patientRequest) {
        LocalDateTime now = LocalDateTime.now();
        Patient patient = Patient.builder()
                .name(patientRequest.getName())
                .surname(patientRequest.getSurname())
                .email(patientRequest.getEmail())
                .pesel(patientRequest.getPesel())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }
}
