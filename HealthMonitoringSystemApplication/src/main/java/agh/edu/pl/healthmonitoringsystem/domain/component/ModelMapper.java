package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Author;
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
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientDataProjection;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.HealthCommentWithAuthorProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataType;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ModelMapper {

    public ModelMapper() {}

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
        Author doctor = new Author(referral.getDoctorId(), referral.getDoctorName(), referral.getDoctorSurname());
        Comment comment = referral.getComment() != null ? new Comment(referral.getId(), doctor, referral.getModifiedDate(), referral.getComment()) : null;
        return new Referral(
                referral.getId(),
                referral.getPatientId(),
                referral.getTestType(),
                referral.getReferralNumber(),
                referral.getCompleted(),
                doctor,
                comment,
                referral.getCreatedDate());
    }

    public Comment mapProjectionToHealth(HealthCommentWithAuthorProjection healthComment) {
        if (healthComment == null) { return null; }
        Author doctor = new Author(healthComment.getDoctorId(), healthComment.getDoctorName(), healthComment.getDoctorSurname());
        return new Comment(healthComment.getHealthCommentId(), doctor, healthComment.getModifiedDate(), healthComment.getContent());
    }

    public ResultForDoctorView mapProjectionToResultForDoctorView(ResultWithAiSelectedAndViewedProjection result) {
        if (result == null) { return null; }
        return new ResultForDoctorView(
                result.getId(),
                result.getTestType(),
                new ResultDataContent(ResultDataType.fromString(result.getDataType()), result.getData()),
                result.getAiSelected(),
                result.getViewed(),
                result.getCreatedDate());
    }

    public ResultWithPatientData mapProjectionToResultWithPatientData(ResultWithPatientDataProjection result) {
        if (result == null) { return null; }
        return new ResultWithPatientData(result.getId(),
                new Patient(result.getId(), result.getName(), result.getSurname(), result.getEmail(), result.getPesel()),
                result.getTestType(),
                new ResultDataContent(ResultDataType.fromString(result.getDataType()), result.getData()),
                result.getCreatedDate());
    }

    public Relation mapRelationEntityToRelation(DoctorPatientEntity relation) {
        if (relation == null) { return null; }
        return new Relation(relation.getPatientId(), relation.getDoctorId());
    }
}
