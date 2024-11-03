package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultRequestValidator {

    private final EntityValidator entityValidator;

    @Autowired
    public ResultRequestValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public void validate(ResultRequest request) {
        entityValidator.validateResult(request.getResultId());
        entityValidator.validatePatient(request.getPatientId());
        entityValidator.validateDoctor(request.getDoctorId());
    }
}
