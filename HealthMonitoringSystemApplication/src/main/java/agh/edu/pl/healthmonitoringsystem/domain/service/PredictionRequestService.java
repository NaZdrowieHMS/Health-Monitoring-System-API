package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionSummaryEntryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionSummaryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionSummaryEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionSummaryEntryEntity;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static agh.edu.pl.healthmonitoringsystem.domain.component.UpdateUtil.updateField;
import static agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus.IN_PROGRESS;

@Service
public class PredictionRequestService {

    private final PredictionSummaryRepository predictionRepository;
    private final PredictionSummaryEntryRepository predictionEntryRepository;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    @Autowired
    public PredictionRequestService(PredictionSummaryRepository predictionRepository, PredictionSummaryEntryRepository predictionEntryRepository, ModelMapper modelMapper, RequestValidator validator) {
        this.predictionRepository = predictionRepository;
        this.predictionEntryRepository = predictionEntryRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public PredictionSummary getPredictionSummaryRequestById(Long requestId) {
        AiPredictionSummaryEntity entity = predictionRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("AI prediction Summary reqest with id " + requestId + " not found"));

        List<AiPredictionSummaryEntryEntity> entries = predictionEntryRepository.findByRequestId(requestId);

        return modelMapper.mapPredictionSummaryEntityToPredictionSummary(entity, entries);
    }

    public PredictionSummary createPredictionRequest(PredictionSummaryRequest predictionSummaryRequest) {
        validator.validate(predictionSummaryRequest);

        LocalDateTime now = LocalDateTime.now();
        AiPredictionSummaryEntity entity = AiPredictionSummaryEntity.builder()
                .patientId(predictionSummaryRequest.patientId())
                .doctorId(predictionSummaryRequest.doctorId())
                .status(IN_PROGRESS.toString())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        AiPredictionSummaryEntity savedEntity = predictionRepository.save(entity);

        List<AiPredictionSummaryEntryEntity> entries = predictionSummaryRequest.resultIds().stream()
                .map(resultId -> AiPredictionSummaryEntryEntity.builder()
                        .requestId(savedEntity.getId())
                        .resultId(resultId)
                        .build())
                .toList();

        predictionEntryRepository.saveAll(entries);

        return modelMapper.mapPredictionSummaryEntityToPredictionSummary(entity, entries);
    }

    public void updatePredictionRequest(PredictionSummaryUpdateRequest updateRequest) {
        AiPredictionSummaryEntity predictionSummaryEntity = predictionRepository.findById(updateRequest.getRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Prediction summary request with id " + updateRequest.getRequestId() + " does not exist"));

        updateField(Optional.ofNullable(updateRequest.getStatus().toString()), predictionSummaryEntity::setStatus);
        updateField(Optional.ofNullable(updateRequest.getPrediction()), predictionSummaryEntity::setPrediction);
        updateField(Optional.ofNullable(updateRequest.getConfidence()), predictionSummaryEntity::setConfidence);
        predictionSummaryEntity.setModifiedDate(LocalDateTime.now());

        predictionRepository.save(predictionSummaryEntity);
    }

    public List<PredictionSummary> getPatientPredictions(Long patientId, Integer page, Integer size) {
        validator.validatePatient(patientId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<AiPredictionSummaryEntity> predictionEntities = predictionRepository.findByPatientId(patientId, pageable);

        return predictionEntities.stream()
                .map(predictionEntity -> {
                    List<AiPredictionSummaryEntryEntity> entries = predictionEntryRepository.findByRequestId(predictionEntity.getId());
                    return modelMapper.mapPredictionSummaryEntityToPredictionSummary(predictionEntity, entries);
                })
                .toList();
    }
}
