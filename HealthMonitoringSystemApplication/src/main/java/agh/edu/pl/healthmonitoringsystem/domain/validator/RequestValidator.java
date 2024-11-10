package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.InvalidImageException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PredictionRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultCommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.FormRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestValidator extends EntityValidator {

    public RequestValidator(ResultRepository resultRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ReferralRepository referralRepository, FormRepository formRepository) {
        super(resultRepository, patientRepository, doctorRepository, referralRepository, formRepository);
    }

    public void validate(PredictionRequest request) {
        if (request.getImageBase64() == null || request.getImageBase64().isEmpty()) {
            log.error("Validation for prediction request failed");
            throw new InvalidImageException("No image provided");
        }
    }

    public void validate(ResultUploadRequest request) {
        validatePatient(request.getPatientId());
        if(request.getReferralId() != null){
            validateReferral(request.getReferralId());
        }
    }

    public void validate(Long doctorId, Long patientId) {
        validatePatient(patientId);
        validateDoctor(doctorId);
    }

    public void validate(AiFormAnalysisRequest request) {
        validateForm(request.getFormId());
        validatePatient(request.getPatientId());
    }

    public void validate(ResultCommentRequest request) {
        validateResult(request.getResultId());
        validateDoctor(request.getDoctorId());
    }

    public void validate(ResultRequest request) {
        validateResult(request.getResultId());
        validatePatient(request.getPatientId());
        validateDoctor(request.getDoctorId());
    }

    public void validateCommentUpdateRequest(CommentUpdateRequest request, Long authorId) {
        if (!authorId.equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the health comment can edit it. " +
                    "Author id: %s. Current editor id: %s.", authorId, request.getDoctorId()));
        }
    }

    public void validateUpdateRequest(ReferralUpdateRequest request, ReferralEntity referralEntity) {
        if (!referralEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the referral can edit it. " +
                    "Author id: %s. Current editor id: %s.", referralEntity.getDoctorId(), request.getDoctorId()));
        }
    }
}