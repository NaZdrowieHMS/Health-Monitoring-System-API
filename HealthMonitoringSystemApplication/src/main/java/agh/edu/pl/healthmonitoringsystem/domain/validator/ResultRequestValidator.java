package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultRequestValidator {

    private final ResultRepository resultRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ResultRequestValidator(ResultRepository resultRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.resultRepository = resultRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public void validate(ResultRequest request) {
        resultRepository.findById(request.getResultId())
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + request.getResultId() + " does not exist"));

        patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + request.getPatientId() + " does not exist"));

        doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + request.getDoctorId() + " does not exist"));
    }
}
