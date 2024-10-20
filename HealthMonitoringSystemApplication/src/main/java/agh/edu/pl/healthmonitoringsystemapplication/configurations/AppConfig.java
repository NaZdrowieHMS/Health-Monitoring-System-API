package agh.edu.pl.healthmonitoringsystemapplication.configurations;

import agh.edu.pl.healthmonitoringsystemapplication.services.PredictionService;
import agh.edu.pl.healthmonitoringsystemapplication.tools.ai_model.ModelPredictor;
import agh.edu.pl.healthmonitoringsystemapplication.tools.image.ImageDecoder;
import agh.edu.pl.healthmonitoringsystemapplication.tools.image.ImagePreprocessor;
import agh.edu.pl.healthmonitoringsystemapplication.validators.PredictionRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public ImageDecoder imageDecoder() {
        return new ImageDecoder();
    }

    @Bean
    @Scope("singleton")
    public ImagePreprocessor imagePreprocessor() {
        return new ImagePreprocessor();
    }

    @Bean
    @Scope("singleton")
    public ModelPredictor modelPredictor() {
        return new ModelPredictor();
    }

    @Bean
    @Scope("singleton")
    public PredictionService predictionService(ImageDecoder imageDecoder, ImagePreprocessor imagePreprocessor, ModelPredictor modelPredictor,
                                               PredictionRequestValidator predictionRequestValidator) {
        return new PredictionService(imageDecoder, imagePreprocessor, modelPredictor, predictionRequestValidator);
    }

    @Bean
    @Scope("singleton")
    public PredictionRequestValidator predictionRequestValidator() {
        return new PredictionRequestValidator();
    }
}
