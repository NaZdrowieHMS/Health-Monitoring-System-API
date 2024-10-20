package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionResponse;
import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionRequest;
import agh.edu.pl.healthmonitoringsystemapplication.tools.ai_model.ModelPredictor;
import agh.edu.pl.healthmonitoringsystemapplication.tools.image.ImageDecoder;
import agh.edu.pl.healthmonitoringsystemapplication.tools.image.ImagePreprocessor;
import agh.edu.pl.healthmonitoringsystemapplication.validators.PredictionRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tensorflow.Tensor;

import java.awt.image.BufferedImage;

@Slf4j
@Service
public class PredictionService {

    private final ImageDecoder imageDecoder;
    private final ImagePreprocessor imagePreprocessor;
    private final ModelPredictor modelPredictor;
    private final PredictionRequestValidator predictionRequestValidator;

    private final String[] MODEL_CLASSES = {"benign", "malignant", "normal"};

    @Autowired
    public PredictionService(ImageDecoder imageDecoder, ImagePreprocessor imagePreprocessor, ModelPredictor modelPredictor,
                             PredictionRequestValidator predictionRequestValidator) {
        this.imageDecoder = imageDecoder;
        this.imagePreprocessor = imagePreprocessor;
        this.modelPredictor = modelPredictor;
        this.predictionRequestValidator = predictionRequestValidator;
    }

    public PredictionResponse predict(PredictionRequest request) {
        log.info("Received a test prediction request");
        predictionRequestValidator.validate(request);

        BufferedImage image = imageDecoder.decodeBase64Image(request.getImageBase64());
        Tensor inputTensor = imagePreprocessor.preprocessImage(image);
        float[] predictions = modelPredictor.predict(inputTensor);

        int predictedClass = argMax(predictions);
        String predictionLabel = MODEL_CLASSES[predictedClass];

        return PredictionResponse.builder()
                .success(true)
                .prediction(predictionLabel)
                .confidence(predictions[predictedClass])
                .build();
    }

    private int argMax(float[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
