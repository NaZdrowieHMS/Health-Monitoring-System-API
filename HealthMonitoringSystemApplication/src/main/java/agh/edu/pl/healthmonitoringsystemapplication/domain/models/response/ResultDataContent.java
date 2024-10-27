package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import lombok.Getter;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Getter
public class ResultDataContent {
    private final String data;
    private final String type; //TODO: Enum in the future

    @lombok.Builder(builderClassName = "Builder")
    private ResultDataContent(String data, String type) {
        this.data = data;
        this.type = type;
    }

    public static final class Builder {
        public ResultDataContent build() {
            checkNotNull(data, () -> new RequestValidationException("Data cannot be null"));
            checkNotNull(type, () -> new RequestValidationException("Data type id cannot be null"));
            return new ResultDataContent(data, type);
        }
    }
}