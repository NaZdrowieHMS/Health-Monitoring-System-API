package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.InvalidImageException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PredictionRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestValidator {

    private final EntityValidator entityValidator;

    @Autowired
    public RequestValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public void validate(PredictionRequest request) {
        if (request.getImageBase64() == null || request.getImageBase64().isEmpty()) {
            log.error("Validation for prediction request failed");
            throw new InvalidImageException("No image provided");
        }
    }

    public void validate(ResultUploadRequest request) {
        entityValidator.validatePatient(request.getPatientId());
        if(request.getReferralId() != null){
            entityValidator.validateReferral(request.getReferralId());
        }
    }

    public void validate(Long doctorId, Long patientId) {
        entityValidator.validatePatient(patientId);
        entityValidator.validateDoctor(doctorId);
    }

    public void validate(AiFormAnalysisRequest request) {
        entityValidator.validateForm(request.getFormId());
        entityValidator.validatePatient(request.getPatientId());
    }

    public void validate(ResultRequest request) {
        entityValidator.validateResult(request.getResultId());
        entityValidator.validatePatient(request.getPatientId());
        entityValidator.validateDoctor(request.getDoctorId());
    }

    public void validateUpdateRequest(CommentUpdateRequest request, HealthCommentEntity healthCommentEntity) {
        if (!healthCommentEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the health comment can edit it. " +
                    "Author id: %s. Current editor id: %s.", healthCommentEntity.getDoctorId(), request.getDoctorId()));
        }
    }

    public void validateUpdateRequest(ReferralUpdateRequest request, ReferralEntity referralEntity) {
        if (!referralEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the referral can edit it. " +
                    "Author id: %s. Current editor id: %s.", referralEntity.getDoctorId(), request.getDoctorId()));
        }
    }

    public void validatePatient(Long patientId) {
        entityValidator.validatePatient(patientId);
    }

    public void validateDoctor(Long doctorId) {
        entityValidator.validatePatient(doctorId);
    }
}