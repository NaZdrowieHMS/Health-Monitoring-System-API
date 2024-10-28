package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validators.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "referral_comment")
@Getter
@Setter
public class ReferralCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ReferralCommentEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ReferralCommentEntity(Long id, Long doctorId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public ReferralCommentEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ReferralCommentEntity(id, doctorId, content, createdDate, modifiedDate);
        }
    }
}
