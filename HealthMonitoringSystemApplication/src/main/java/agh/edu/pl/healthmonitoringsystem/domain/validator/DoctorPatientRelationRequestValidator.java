package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorPatientRelationRequestValidator {

    private final EntityValidator entityValidator;

    @Autowired
    public DoctorPatientRelationRequestValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public void  validate(DoctorPatientRelationRequest request) {
        entityValidator.validatePatient(request.getPatientId());
        entityValidator.validateDoctor(request.getDoctorId());
    }

    public void  validate(Long doctorId, Long patientId) {
        entityValidator.validatePatient(patientId);
        entityValidator.validateDoctor(doctorId);
    }
}