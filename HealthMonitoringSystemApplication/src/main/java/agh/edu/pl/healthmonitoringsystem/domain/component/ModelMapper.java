package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.HealthComment;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Author;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientDataProjection;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
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
        return new Patient(patient.getId(), patient.getName(), patient.getSurname(), patient.getEmail(), patient.getPesel());
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

        return new Result(
                result.getId(),
                result.getPatientId(),
                result.getTestType(),
                new ResultDataContent(dataValue, typeValue),
                result.getCreatedDate()
        );
    }

    public Referral mapProjectionToReferral(PatientReferralWithCommentProjection referral) {
        if (referral == null) { return null; }
        Author doctor = new Author(referral.getDoctorId(), referral.getDoctorName(), referral.getDoctorSurname());
        return new Referral(
                referral.getReferralId(),
                referral.getPatientId(),
                referral.getTestType(),
                referral.getReferralNumber(),
                referral.getCompleted(),
                doctor,
                new Comment(doctor, referral.getModifiedDate(), referral.getComment()),
                referral.getCreatedDate());

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

    public ResultForDoctorView mapProjectionToResultForDoctorView(ResultWithAiSelectedAndViewedProjection result) {
        if (result == null) { return null; }
        String jsonContent = result.getContent();
        String dataValue = jsonFieldExtractor.extract(jsonContent, "data");
        String typeValue = jsonFieldExtractor.extract(jsonContent, "type");

        return new ResultForDoctorView(
                result.getId(),
                result.getTestType(),
                new ResultDataContent(dataValue, typeValue),
                result.getAiSelected(),
                result.getViewed(),
                result.getCreatedDate());
    }

    public ResultWithPatientData mapProjectionToResultWithPatientData(ResultWithPatientDataProjection result) {
        if (result == null) { return null; }
        String jsonContent = result.getContent();
        String dataValue = jsonFieldExtractor.extract(jsonContent, "data");
        String typeValue = jsonFieldExtractor.extract(jsonContent, "type");

        return new ResultWithPatientData(result.getId(),
                new Patient(result.getId(), result.getName(), result.getSurname(), result.getEmail(), result.getPesel()),
                result.getTestType(),
                new ResultDataContent(dataValue, typeValue),
                result.getCreatedDate());
    }
}
