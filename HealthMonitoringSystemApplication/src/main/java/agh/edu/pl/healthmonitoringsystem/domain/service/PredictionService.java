package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.NotPredictedException;
import agh.edu.pl.healthmonitoringsystem.request.BatchPredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository, ModelMapper modelMapper, RequestValidator validator) {
        this.predictionRepository = predictionRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public Prediction getPredictionByResultId(Long resultId) {
        validator.validateResult(resultId);
        Optional<AiPredictionEntity> entity = predictionRepository.findByResultId(resultId);
        return entity.map(modelMapper::mapPredictionEntityToPrediction).orElse(null);
    }

    public Prediction uploadPrediction(PredictionUploadRequest predictionRequest) {
        validator.validate(predictionRequest);

        AiPredictionEntity predictionEntity  = AiPredictionEntity.builder()
                .doctorId(predictionRequest.getDoctorId())
                .resultId(predictionRequest.getResultId())
                .confidence(predictionRequest.getConfidence())
                .prediction(predictionRequest.getPrediction())
                .createdDate(LocalDateTime.now())
                .build();

        return saveAndMapPrediction(predictionEntity);
    }

    public List<Prediction> batchUploadPredictions(BatchPredictionUploadRequest predictionRequest) {
        validator.validate(predictionRequest);

        List<AiPredictionEntity> predictionEntities = predictionRequest.getPredictions().stream()
                .map(request -> AiPredictionEntity.builder()
                        .doctorId(request.getDoctorId())
                        .resultId(request.getResultId())
                        .confidence(request.getConfidence())
                        .prediction(request.getPrediction())
                        .createdDate(LocalDateTime.now())
                        .build())
                .toList();

        List<AiPredictionEntity> savedEntities = predictionRepository.saveAll(predictionEntities);

        return savedEntities.stream()
                .map(modelMapper::mapPredictionEntityToPrediction)
                .toList();
    }


    public void deletePrediction(Long predictionId) {
        validator.validatePrediction(predictionId);

        AiPredictionEntity entity = predictionRepository.findById(predictionId)
                .orElseThrow(() -> new EntityNotFoundException("AI Prediction with id " + predictionId + " does not exist"));
        predictionRepository.delete(entity);
    }

    private Prediction saveAndMapPrediction(AiPredictionEntity entity) {
        AiPredictionEntity savedEntity = predictionRepository.save(entity);
        return modelMapper.mapPredictionEntityToPrediction(savedEntity);
    }
}
