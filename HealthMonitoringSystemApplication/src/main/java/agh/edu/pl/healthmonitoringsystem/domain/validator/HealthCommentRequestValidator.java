package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthCommentRequestValidator {
    private final EntityValidator entityValidator;

    @Autowired
    public HealthCommentRequestValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public void validate(CommentRequest request) {
        entityValidator.validatePatient(request.getPatientId());
        entityValidator.validateDoctor(request.getDoctorId());
    }

    public void validateUpdateRequest(CommentUpdateRequest request, HealthCommentEntity healthCommentEntity) {
        if (!healthCommentEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the health comment can edit it. " +
                    "Author id: %s. Current editor id: %s.", healthCommentEntity.getDoctorId(), request.getDoctorId()));
        }
    }
}