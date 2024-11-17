package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "ai_prediction_summary_entry")
@Getter
@Setter
public class AiPredictionSummaryEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long requestId;
    private Long resultId;

    public AiPredictionSummaryEntryEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPredictionSummaryEntryEntity(Long id, Long requestId, Long resultId) {
        this.id = id;
        this.requestId = requestId;
        this.resultId = resultId;
    }

    public static final class Builder {
        public AiPredictionSummaryEntryEntity build() {
            checkNotNull(requestId, () -> new RequestValidationException("Request ID cannot be null"));
            checkNotNull(resultId, () -> new RequestValidationException("Result ID cannot be null"));
            return new AiPredictionSummaryEntryEntity(id, requestId, resultId);
        }
    }
}
