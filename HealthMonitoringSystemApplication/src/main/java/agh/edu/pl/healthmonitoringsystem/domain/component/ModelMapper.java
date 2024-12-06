package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.*;
import agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.*;
import agh.edu.pl.healthmonitoringsystem.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.response.DetailedResult;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import agh.edu.pl.healthmonitoringsystem.response.FormEntry;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import org.springframework.stereotype.Component;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.enums.ResultDataType.fromString;


@Component
public class ModelMapper {
    private final JsonConverter jsonConverter;

    public ModelMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public Patient mapUserEntityToPatient(UserEntity patient) {
        if (patient == null) { return null; }
        return new Patient(patient.getId(), patient.getName(), patient.getSurname(),
                patient.getEmail(), patient.getPesel());
    }

    public Doctor mapUserEntityToDoctor(UserEntity doctor) {
        if (doctor == null) { return null; }
        return new Doctor(doctor.getId(), doctor.getName(), doctor.getSurname(),
                doctor.getEmail(), doctor.getPesel(), doctor.getPwz());
    }

    public ResultOverview mapResultEntityToResultOverview(ResultEntity result) {
        if (result == null) { return null; }
        return new ResultOverview(
                result.getId(),
                result.getPatientId(),
                result.getTestType(),
                result.getCreatedDate()
        );
    }

    public DetailedResult mapResultEntityToDetailedResult(ResultEntity result) {
        if (result == null) { return null; }
        return new DetailedResult(
                result.getId(),
                result.getPatientId(),
                result.getTestType(),
                new ResultDataContent(fromString(result.getDataType()), result.getData()),
                result.getCreatedDate()
        );
    }

    public DetailedResult mapResultEntityToDetailedResult(Boolean aiSelected, Boolean viewed, ResultEntity result) {
        if (result == null) { return null; }
        return new DetailedResult(
                result.getId(),
                result.getPatientId(),
                result.getTestType(),
                new ResultDataContent(fromString(result.getDataType()), result.getData()),
                result.getCreatedDate(),
                aiSelected,
                viewed
        );
    }

    public Form mapFormEntityToForm(FormEntity formEntity, List<FormEntryEntity> formEntities) {
        if (formEntity == null || formEntities == null) { return null; }
        List<FormEntry> formEntries = formEntities
                .stream()
                .map(this::mapFormEntryEntityToFormEntry)
                .toList();
        return new Form(formEntity.getId(), formEntity.getPatientId(), formEntity.getCreatedDate(), formEntries);
    }

    private FormEntry mapFormEntryEntityToFormEntry(FormEntryEntity formEntryEntity) {
        if (formEntryEntity == null) { return null; }
        return new FormEntry(formEntryEntity.getHealthParam(), formEntryEntity.getValue());
    }

    public PredictionSummary mapPredictionSummaryEntityToPredictionSummary(PredictionSummaryEntity predictionEntity) {
        if (predictionEntity == null) { return null; }

        return new PredictionSummary(predictionEntity.getId(),
                predictionEntity.getStatus(),
                predictionEntity.getPatientId(),
                predictionEntity.getDoctorId(),
                predictionEntity.getCreatedDate(),
                predictionEntity.getModifiedDate(),
                jsonConverter.convertToEntityAttribute(predictionEntity.getResultAiAnalysis()),
                null);
    }

    public Referral mapProjectionToReferral(PatientReferralWithCommentProjection referral) {
        if (referral == null) { return null; }
        Author doctor = new Author(referral.doctorId(), referral.doctorName(), referral.doctorSurname());
        Comment comment = referral.comment() != null ? new Comment(referral.id(), doctor, referral.modifiedDate(), referral.comment()) : null;
        return new Referral(
                referral.id(),
                referral.patientId(),
                referral.testType(),
                referral.referralNumber(),
                referral.completed(),
                doctor,
                comment,
                referral.createdDate());
    }

    public Comment mapProjectionToComment(CommentWithAuthorProjection healthComment) {
        if (healthComment == null) { return null; }
        Author doctor = new Author(healthComment.doctorId(), healthComment.doctorName(), healthComment.doctorSurname());
        return new Comment(healthComment.commentId(), doctor, healthComment.modifiedDate(), healthComment.content());
    }

    public DetailedResult mapProjectionToDetailedResult(ResultWithAiSelectedAndViewedProjection result) {
        if (result == null) { return null; }
        return new DetailedResult(
                result.id(),
                result.patientId(),
                result.testType(),
                new ResultDataContent(fromString(result.dataType()), result.data()),
                result.createdDate(),
                result.aiSelected(),
                result.viewed());
    }

    public Relation mapRelationEntityToRelation(DoctorPatientEntity relation) {
        if (relation == null) { return null; }
        return new Relation(relation.getPatientId(), relation.getDoctorId());
    }

//    public FormAiAnalysis mapAiAnalysisEntityToAiFormAnalysis(AiFormAnalysisEntity aiAnalysis) {
//        if (aiAnalysis == null) { return null; }
//        List<String> diagnoses = jsonConverter.convertToEntityAttribute(aiAnalysis.getDiagnoses());
//        List<String> recommendations = jsonConverter.convertToEntityAttribute(aiAnalysis.getRecommendations());
//
//        return new FormAiAnalysis(aiAnalysis.getId(), aiAnalysis.getPatientId(), aiAnalysis.getFormId(),
//                diagnoses, recommendations, aiAnalysis.getCreatedDate());
//    }

    public Prediction mapPredictionEntityToPrediction(AiPredictionEntity aiPrediction) {
        if (aiPrediction == null) { return null; }
        return new Prediction(aiPrediction.getId(), aiPrediction.getResultId(), aiPrediction.getPrediction(),
                aiPrediction.getConfidence(), aiPrediction.getCreatedDate());
    }
}
