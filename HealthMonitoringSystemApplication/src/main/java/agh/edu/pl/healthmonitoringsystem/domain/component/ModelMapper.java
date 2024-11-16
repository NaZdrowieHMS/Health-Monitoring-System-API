package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Author;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiFormAnalysisEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientProjection;
import agh.edu.pl.healthmonitoringsystem.response.AiFormAnalysis;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import agh.edu.pl.healthmonitoringsystem.response.FormEntry;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Relation;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorPatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntryEntity;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataType;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ModelMapper {
    private final JsonConverter jsonConverter;

    public ModelMapper(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public Patient mapPatientEntityToPatient(PatientEntity patient) {
        if (patient == null) { return null; }
        return new Patient(patient.getId(), patient.getName(), patient.getSurname(),
                patient.getEmail(), patient.getPesel());
    }

    public Doctor mapDoctorEntityToDoctor(DoctorEntity doctor) {
        if (doctor == null) { return null; }
        return new Doctor(doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getEmail(),
                doctor.getPesel(), doctor.getPwz());
    }

    public Result mapResultEntityToResult(ResultEntity result) {
        if (result == null) { return null; }
        return new Result(
                result.getId(),
                result.getPatientId(),
                result.getTestType(),
                result.getCreatedDate(),
                new ResultDataContent(ResultDataType.fromString(result.getDataType()), result.getData()));
    }


    public Form mapFormEntityToForm(FormEntity formEntity, List<FormEntryEntity> formEntityEntities) {
        if (formEntity == null || formEntityEntities == null) { return null; }
        List<FormEntry> formEntries = formEntityEntities
                .stream()
                .map(this::mapFormEntryEntityToFormEntry)
                .toList();
        return new Form(formEntity.getId(), formEntity.getPatientId(), formEntity.getCreatedDate(), formEntries);
    }

    private FormEntry mapFormEntryEntityToFormEntry(FormEntryEntity formEntryEntity) {
        if (formEntryEntity == null) { return null; }
        return new FormEntry(formEntryEntity.getHealthParam(), formEntryEntity.getValue());
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

    public ResultForDoctorView mapProjectionToResultForDoctorView(ResultWithAiSelectedAndViewedProjection result) {
        if (result == null) { return null; }
        return new ResultForDoctorView(
                result.id(),
                result.testType(),
                new ResultDataContent(ResultDataType.fromString(result.dataType()), result.data()),
                result.aiSelected(),
                result.viewed(),
                result.createdDate());
    }

    public ResultWithPatientData mapProjectionToResultWithPatientData(ResultWithPatientProjection result) {
        if (result == null) { return null; }
        return new ResultWithPatientData(result.id(),
                new Patient(result.id(), result.name(), result.surname(), result.email(), result.pesel()),
                result.testType(),
                new ResultDataContent(ResultDataType.fromString(result.dataType()), result.data()),
                result.createdDate());
    }

    public Relation mapRelationEntityToRelation(DoctorPatientEntity relation) {
        if (relation == null) { return null; }
        return new Relation(relation.getPatientId(), relation.getDoctorId());
    }

    public AiFormAnalysis mapAiAnalysisEntityToAiFormAnalysis(AiFormAnalysisEntity aiAnalysis) {
        if (aiAnalysis == null) { return null; }
        List<String> diagnoses = jsonConverter.convertToEntityAttribute(aiAnalysis.getDiagnoses());
        List<String> recommendations = jsonConverter.convertToEntityAttribute(aiAnalysis.getRecommendations());

        return new AiFormAnalysis(aiAnalysis.getId(), aiAnalysis.getPatientId(), aiAnalysis.getFormId(),
                diagnoses, recommendations, aiAnalysis.getCreatedDate());
    }

    public Prediction mapPredictionEntityToPrediction(AiPredictionEntity aiPrediction) {
        if (aiPrediction == null) { return null; }
        return new Prediction(aiPrediction.getId(), aiPrediction.getResultId(), aiPrediction.getPrediction(),
                aiPrediction.getConfidence(), aiPrediction.getCreatedDate());
    }
}
