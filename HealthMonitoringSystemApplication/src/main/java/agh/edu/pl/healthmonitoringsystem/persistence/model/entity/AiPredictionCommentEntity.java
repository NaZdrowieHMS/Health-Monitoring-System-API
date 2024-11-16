package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "ai_prediction_comment")
@Getter
@Setter
public class AiPredictionCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private Long predictionId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AiPredictionCommentEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPredictionCommentEntity(Long id, Long predictionId, Long doctorId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
            this.id = id;
            this.doctorId = doctorId;
            this.predictionId = predictionId;
            this.content = content;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
    }
    public static final class Builder {
        public AiPredictionCommentEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(predictionId, () -> new RequestValidationException("Prediction Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new AiPredictionCommentEntity(id, predictionId, doctorId, content, createdDate, modifiedDate);
        }
    }
}
