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
@Table(name = "result_comment")
@Getter
@Setter
public class ResultCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long resultId;
    private Long doctorId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ResultCommentEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ResultCommentEntity(Long id, Long resultId, Long doctorId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.resultId = resultId;
        this.doctorId = doctorId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public ResultCommentEntity build(){
            checkNotNull(resultId, () -> new RequestValidationException("ResultEntity Id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ResultCommentEntity(id, resultId, doctorId, content, createdDate, modifiedDate);
        }
    }
}
