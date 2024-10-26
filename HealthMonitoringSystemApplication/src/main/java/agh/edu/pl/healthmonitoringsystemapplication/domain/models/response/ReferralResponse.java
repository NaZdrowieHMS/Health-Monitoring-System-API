package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Getter
public class ReferralResponse {
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
    private ReferralResponse(Long referralId, Long commentId, Long doctorId, Long patientId, String testType,
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
        public ReferralResponse build(){
            checkNotNull(referralId, () -> new RequestValidationException("Referral Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("Patient id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(referralNumber, () -> new RequestValidationException("Referral Number cannot be null"));
            checkNotNull(completed, () -> new RequestValidationException("Completed cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ReferralResponse(referralId, commentId, doctorId, patientId, testType, referralNumber,
                    completed, commentContent, modifiedDate);
        }
    }
}


