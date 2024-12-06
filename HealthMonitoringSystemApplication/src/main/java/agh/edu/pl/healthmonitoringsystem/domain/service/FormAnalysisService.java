package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.JsonConverter;
import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.AiFormAnalysisRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiFormAnalysisEntity;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FormAnalysisService {

    private final AiFormAnalysisRepository formAnalysisRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;
    private final JsonConverter jsonConverter;

    @Autowired
    public FormAnalysisService(AiFormAnalysisRepository formAnalysisRepository, RequestValidator validator,
                               ModelMapper modelMapper, JsonConverter jsonConverter) {
        this.formAnalysisRepository = formAnalysisRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.jsonConverter = jsonConverter;
    }

//    public FormAiAnalysis saveFormAiAnalysis(AiFormAnalysisRequest aiFormAnalysisRequest) {
//        validator.validate(aiFormAnalysisRequest);
//
//        String diagnosesJson = jsonConverter.convertToDatabaseColumn(aiFormAnalysisRequest.getDiagnoses());
//        String recommendationsJson = jsonConverter.convertToDatabaseColumn(aiFormAnalysisRequest.getRecommendations());
//
//        AiFormAnalysisEntity aiAnalysisEntity = AiFormAnalysisEntity.builder()
//                .formId(aiFormAnalysisRequest.getFormId())
//                .patientId(aiFormAnalysisRequest.getPatientId())
//                .doctorId(aiFormAnalysisRequest.getDoctorId())
//                .diagnoses(diagnosesJson)
//                .recommendations(recommendationsJson)
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        AiFormAnalysisEntity aiAnalysisEntitySaved = formAnalysisRepository.save(aiAnalysisEntity);
//
//        return modelMapper.mapAiAnalysisEntityToAiFormAnalysis(aiAnalysisEntitySaved);
//    }
//
//    public FormAiAnalysis getFormLastAiAnalysisById(Long doctorId, Long formId) {
//        validator.validateDoctor(doctorId);
//
//        AiFormAnalysisEntity aiAnalysisEntity = formAnalysisRepository.findTopByFormIdAndDoctorId(formId, doctorId)
//                .orElseThrow(() -> new EntityNotFoundException("Ai analysis for form " + formId + " not found"));
//
//        return modelMapper.mapAiAnalysisEntityToAiFormAnalysis(aiAnalysisEntity);
//    }
}
