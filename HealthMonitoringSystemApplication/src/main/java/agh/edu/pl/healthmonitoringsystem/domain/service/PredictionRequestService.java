package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ResultAiAnalysisConverter;
import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.model.ResultAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionSummaryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PredictionSummaryEntity;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static agh.edu.pl.healthmonitoringsystem.domain.component.UpdateUtil.updateField;
import static agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus.IN_PROGRESS;

@Service
public class PredictionRequestService {

    private final PredictionSummaryRepository predictionRepository;
    private final ModelMapper modelMapper;
    private final ResultAiAnalysisConverter resultAiAnalysisConverter;
    private final RequestValidator validator;

    @Autowired
    public PredictionRequestService(PredictionSummaryRepository predictionRepository, ModelMapper modelMapper, ResultAiAnalysisConverter resultAiAnalysisConverter, RequestValidator validator) {
        this.predictionRepository = predictionRepository;
        this.modelMapper = modelMapper;
        this.resultAiAnalysisConverter = resultAiAnalysisConverter;
        this.validator = validator;
    }

    public PredictionSummary getTest() {
        PredictionSummaryEntity entity = predictionRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("AI prediction Summary reqest with id " + 1 + " not found"));

        return modelMapper.mapPredictionSummaryEntityToPredictionSummary(entity);
    }


    public PredictionSummary getPredictionSummaryRequestById(Long requestId) {
        PredictionSummaryEntity entity = predictionRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("AI prediction Summary reqest with id " + requestId + " not found"));

        return modelMapper.mapPredictionSummaryEntityToPredictionSummary(entity);
    }

    public PredictionSummary createPredictionRequest(PredictionSummaryRequest predictionSummaryRequest) {
        validator.validate(predictionSummaryRequest);

        LocalDateTime now = LocalDateTime.now();
        PredictionSummaryEntity entity = PredictionSummaryEntity.builder()
                .patientId(predictionSummaryRequest.patientId())
                .doctorId(predictionSummaryRequest.doctorId())
                .status(IN_PROGRESS)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        ResultAiAnalysis analysis = new ResultAiAnalysis(predictionSummaryRequest.resultIds(), null, null);

        entity.setResultAiAnalysis(resultAiAnalysisConverter.convertToDatabaseColumn(analysis));

        predictionRepository.save(entity);

        return modelMapper.mapPredictionSummaryEntityToPredictionSummary(entity);
    }

    public void updatePredictionRequest(PredictionSummaryUpdateRequest updateRequest) {
        PredictionSummaryEntity predictionSummaryEntity = predictionRepository.findById(updateRequest.getRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Prediction summary request with id " + updateRequest.getRequestId() + " does not exist"));

        ResultAiAnalysis analysis = resultAiAnalysisConverter.convertToEntityAttribute(predictionSummaryEntity.getResultAiAnalysis());

        ResultAiAnalysis updatedAnalysis = new ResultAiAnalysis(analysis.resultIds(), updateRequest.getPrediction(), updateRequest.getConfidence());

        updateField(Optional.ofNullable(updateRequest.getStatus()), predictionSummaryEntity::setStatus);
        predictionSummaryEntity.setResultAiAnalysis(resultAiAnalysisConverter.convertToDatabaseColumn(updatedAnalysis));
        predictionSummaryEntity.setModifiedDate(LocalDateTime.now());

        predictionRepository.save(predictionSummaryEntity);
    }

//    public List<PredictionSummary> getPatientPredictions(Long patientId, Integer page, Integer size) {
//        validator.validatePatient(patientId);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
//        List<AiPredictionSummaryEntity> predictionEntities = predictionRepository.findByPatientId(patientId, pageable);
//
//        return predictionEntities.stream()
//                .map(predictionEntity -> {
//                    List<AiPredictionSummaryEntryEntity> entries = predictionEntryRepository.findByRequestId(predictionEntity.getId());
//                    return modelMapper.mapPredictionSummaryEntityToPredictionSummary(predictionEntity, entries);
//                })
//                .toList();
//    }
}
