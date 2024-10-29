package agh.edu.pl.healthmonitoringsystem.domain.validators;


import agh.edu.pl.healthmonitoringsystem.domain.exceptions.InvalidImageException;
import agh.edu.pl.healthmonitoringsystem.domain.models.request.PredictionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PredictionRequestValidator  {

    public void validate(PredictionRequest request) {
        if (request.getImageBase64() == null || request.getImageBase64().isEmpty()) {
            log.error("Validation for prediction request failed");
            throw new InvalidImageException("No image provided");
        }
    }
}