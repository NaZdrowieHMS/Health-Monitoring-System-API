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
@Table(name = "health_form_entry")
@Getter
@Setter
public class FormEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long formId;
    private String healthParam;
    private String value;

    public FormEntryEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public FormEntryEntity(Long id, Long formId, String healthParam, String value) {
        this.id = id;
        this.formId = formId;
        this.healthParam = healthParam;
        this.value = value;
    }

    public static final class Builder {
        public FormEntryEntity build(){
            checkNotNull(formId, () -> new RequestValidationException("Form Id cannot be null"));
            checkNotNull(healthParam, () -> new RequestValidationException("Health parameter cannot be null"));
            checkNotNull(value, () -> new RequestValidationException("Value cannot be null"));
            return new FormEntryEntity(id, formId, healthParam, value);
        }
    }
}
