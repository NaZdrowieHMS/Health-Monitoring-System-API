package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.domain.components.ModelMapper;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorPatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorPatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientEntity> patients = patientRepository.findPatientsByDoctorId(doctorId, pageRequest).getContent();
        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }
}
