package agh.edu.pl.healthmonitoringsystemapplication.validators;


import agh.edu.pl.healthmonitoringsystemapplication.exceptions.InvalidImageException;
import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionRequest;

public class PredictionRequestValidator  {

    public void validate(PredictionRequest request) {
        if (request.getImageBase64() == null || request.getImageBase64().isEmpty()) {
            throw new InvalidImageException("No image provided");
        }
    }
}
