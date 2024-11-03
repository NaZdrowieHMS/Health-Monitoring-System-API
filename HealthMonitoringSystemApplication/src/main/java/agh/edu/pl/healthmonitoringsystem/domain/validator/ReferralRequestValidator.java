package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReferralRequestValidator {

    private final EntityValidator entityValidator;

    @Autowired
    public ReferralRequestValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public void validate(ReferralRequest request) {
        entityValidator.validatePatient(request.getPatientId());
        entityValidator.validateDoctor(request.getDoctorId());
    }

    public void validateUpdateRequest(ReferralUpdateRequest request, ReferralEntity referralEntity) {
        if (!referralEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the referral can edit it. " +
                    "Author id: %s. Current editor id: %s.", referralEntity.getDoctorId(), request.getDoctorId()));
        }
    }
}
