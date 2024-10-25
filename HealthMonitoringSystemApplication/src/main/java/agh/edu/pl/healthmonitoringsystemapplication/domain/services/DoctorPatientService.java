package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorPatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public DoctorPatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Patient> patientPage = patientRepository.findPatientsByDoctorId(doctorId, pageRequest);
        return patientPage.getContent();

    }
}
