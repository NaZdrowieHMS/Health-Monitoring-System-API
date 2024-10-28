package agh.edu.pl.healthmonitoringsystem.domain.components;

import agh.edu.pl.healthmonitoringsystem.domain.models.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.HealthComment;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Result;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.ResultDataContent;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.HealthCommentWithAuthorProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.springframework.stereotype.Component;


@Component
public class ModelMapper {
    private final JsonFieldExtractor jsonFieldExtractor;

    public ModelMapper(JsonFieldExtractor jsonFieldExtractor) {
        this.jsonFieldExtractor = jsonFieldExtractor;
    }

    public Patient mapPatientEntityToPatient(PatientEntity patient) {
        if (patient == null) { return null; }
        return Patient.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .surname(patient.getSurname())
                        .email(patient.getEmail())
                        .pesel(patient.getPesel())
                        .build();
    }

    public Doctor mapDoctorEntityToDoctor(DoctorEntity doctor) {
        if (doctor == null) { return null; }
        return Doctor.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .email(doctor.getEmail())
                .pesel(doctor.getPesel())
                .pwz(doctor.getPwz())
                .build();
    }

    public Result mapResultEntityToResult(ResultEntity result) {
        if (result == null) { return null; }
        String jsonContent = result.getContent();
        String dataValue = jsonFieldExtractor.extract(jsonContent, "data");
        String typeValue = jsonFieldExtractor.extract(jsonContent, "type");

        return Result.builder()
                .id(result.getId())
                .patientId(result.getPatientId())
                .testType(result.getTestType())
                .createdDate(result.getCreatedDate())
                .content(ResultDataContent.builder()
                        .data(dataValue)
                        .type(typeValue)
                        .build())
                .build();
    }

    public Referral mapProjectionToReferral(PatientReferralWithCommentProjection referral) {
        if (referral == null) { return null; }
        return Referral.builder()
                .referralId(referral.getReferralId())
                .commentId(referral.getCommentId())
                .doctorId(referral.getDoctorId())
                .patientId(referral.getPatientId())
                .testType(referral.getTestType())
                .referralNumber(referral.getReferralNumber())
                .completed(referral.getCompleted())
                .commentContent(referral.getCommentContent())
                .modifiedDate(referral.getModifiedDate())
                .build();
    }

    public HealthComment mapProjectionToHealth(HealthCommentWithAuthorProjection healthComment) {
        if (healthComment == null) { return null; }
        return HealthComment.builder()
                .id(healthComment.getHealthCommentId())
                .patientId(healthComment.getPatientId())
                .modifiedDate(healthComment.getModifiedDate())
                .content(healthComment.getContent())
                .doctor(Doctor.builder()
                        .id(healthComment.getDoctorId())
                        .name(healthComment.getDoctorName())
                        .surname(healthComment.getDoctorSurname())
                        .email(healthComment.getDoctorEmail())
                        .pesel(healthComment.getDoctorPesel())
                        .pwz(healthComment.getPwz())
                        .build())
                .build();
    }
}
