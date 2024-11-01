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
@Table(name = "result_viewed")
@Getter
@Setter
public class ResultViewedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long resultId;

    public ResultViewedEntity() {}

    @lombok.Builder(builderClassName = "Builder")

    public ResultViewedEntity(Long id, Long patientId, Long doctorId, Long resultId) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.resultId = resultId;

    }

    public static final class Builder {
        public ResultViewedEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(resultId, () -> new RequestValidationException("Result Id cannot be null"));
            return new ResultViewedEntity(id, patientId, doctorId, resultId);
        }
    }
}
