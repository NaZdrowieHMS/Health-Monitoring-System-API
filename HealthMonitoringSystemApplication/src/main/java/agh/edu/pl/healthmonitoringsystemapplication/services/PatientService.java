package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorRequest;
import agh.edu.pl.healthmonitoringsystemapplication.resources.patients.models.PatientRequest;
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
        Patient patient = Patient.builder()
                .name(patientRequest.getName())
                .surname(patientRequest.getSurname())
                .email(patientRequest.getEmail())
                .pesel(patientRequest.getPesel())
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }
}
