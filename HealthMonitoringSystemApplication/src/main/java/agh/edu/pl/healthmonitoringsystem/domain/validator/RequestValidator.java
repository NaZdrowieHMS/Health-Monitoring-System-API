package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionSummaryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.request.BatchPredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PredictionCommentRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultCommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.FormRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestValidator extends EntityValidator {

    public RequestValidator(ResultRepository resultRepository, UserRepository userRepository,
                            ReferralRepository referralRepository, FormRepository formRepository, PredictionRepository predictionRepository,
                            PredictionSummaryRepository predictionSummaryRepository) {
        super(resultRepository, userRepository, referralRepository, formRepository, predictionRepository, predictionSummaryRepository);
    }

    public void validate(PredictionUploadRequest request) {
        validateResult(request.getResultId());
        validateDoctor(request.getDoctorId());
    }

    public void validate(BatchPredictionUploadRequest predictionRequest) {
        predictionRequest.getPredictions().forEach(this::validate);
    }

    public void validate(PredictionSummaryRequest predictionRequest) {
        validatePatient(predictionRequest.patientId());
        validateDoctor(predictionRequest.doctorId());
        predictionRequest.resultIds().forEach(resultId -> validateResultForPatient(resultId, predictionRequest.patientId()));
    }

    public void validate(ResultUploadRequest request) {
        validatePatient(request.getPatientId());
        if(request.getReferralId() != null){
            validateReferral(request.getReferralId());
        }
    }

    public void validate(Long userId) {
        validateUser(userId);
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

    public void validate(PredictionCommentRequest request) {
        validatePredictionSummary(request.getPredictionId());
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