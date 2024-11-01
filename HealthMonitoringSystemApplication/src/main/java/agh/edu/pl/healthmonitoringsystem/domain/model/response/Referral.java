package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Getter
public class Referral {
    private final Long referralId;
    private final Long commentId;
    private final Long doctorId;
    private final Long patientId;
    private final String testType;
    private final String referralNumber;
    private final Boolean completed;
    private final String commentContent;
    private final LocalDateTime modifiedDate;

    @lombok.Builder(builderClassName = "Builder")
    private Referral(Long referralId, Long commentId, Long doctorId, Long patientId, String testType,
                     String referralNumber, Boolean completed, String commentContent, LocalDateTime modifiedDate) {
        this.referralId = referralId;
        this.commentId = commentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.testType = testType;
        this.referralNumber = referralNumber;
        this.completed = completed;
        this.commentContent = commentContent;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public Referral build(){
            checkNotNull(referralId, () -> new RequestValidationException("ReferralEntity Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("PatientEntity id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(referralNumber, () -> new RequestValidationException("ReferralEntity Number cannot be null"));
            checkNotNull(completed, () -> new RequestValidationException("Completed cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new Referral(referralId, commentId, doctorId, patientId, testType, referralNumber,
                    completed, commentContent, modifiedDate);
        }
    }
}


