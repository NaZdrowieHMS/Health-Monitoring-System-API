package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    public Patient getPatientById(Long id){
        return patientRepository.findById(id).orElse(null);
    }
}
