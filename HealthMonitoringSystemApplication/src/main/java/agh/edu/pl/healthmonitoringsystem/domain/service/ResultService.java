package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.response.DetailedResult;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultOverview;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.enums.ResultDataType;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ReferralService referralService;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultService(ResultRepository resultRepository, UserRepository userRepository, ReferralService referralService, RequestValidator validator, ModelMapper modelMapper) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.referralService = referralService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<ResultOverview> getAllResultsByPatientId(Long userId, Long patientId, Integer page, Integer size) throws IllegalAccessException {
        validator.validatePatient(patientId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
        if (user.getRole().equals(Role.DOCTOR)) {
            return resultRepository.getResultsByPatientIdWithAiSelectedAndViewed(userId, patientId, pageRequest).stream().toList();
        }

        if(!Objects.equals(patientId, userId)) throw new IllegalAccessException("Patient " + userId + " is not allowed to see patient " + patientId + " results");
        return resultRepository.getResultEntitiesByPatientId(patientId, pageRequest).stream()
                .map(modelMapper::mapResultEntityToResultOverview)
                .collect(Collectors.toList());
    }

    public DetailedResult getResultByResultId(Long userId, Long resultId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
        if (user.getRole().equals(Role.DOCTOR)) {
            ResultWithAiSelectedAndViewedProjection entity = resultRepository.getResultByIdWithAiSelectedAndViewed(resultId).orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
            return modelMapper.mapProjectionToDetailedResult(entity);
        }

        ResultEntity entity = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));

        if(!Objects.equals(entity.getPatientId(), userId)) throw new IllegalAccessException("Patient " + userId + " is not allowed to see patient " + entity.getPatientId() + " results");

        return modelMapper.mapResultEntityToDetailedResult(entity);
    }

    public List<ResultOverview> getUnviewedResults(Long userId, Integer page, Integer size) throws IllegalAccessException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
        if (user.getRole().equals(Role.DOCTOR)) {
            return resultRepository.getUnviewedResults(userId, pageRequest).stream().toList();
        }

        throw new IllegalAccessException("Patient " + userId + " is not allowed to see unviewed results");
    }

    public DetailedResult uploadResult(Long userId, ResultUploadRequest resultRequest) {
        validator.validate(resultRequest);
        if(resultRequest.getReferralId() != null){
            referralService.completeReferral(resultRequest.getReferralId());
        }

        LocalDateTime now = LocalDateTime.now();
        ResultEntity resultEntity = ResultEntity.builder()
                .patientId(resultRequest.getPatientId())
                .testType(resultRequest.getTestType())
                .dataType(String.valueOf(resultRequest.getContent().getType()))
                .data(resultRequest.getContent().getData())
                .createdDate(now)
                .modifiedDate(now).build();

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
        if (user.getRole().equals(Role.DOCTOR)) {
            return saveAndMapResultEntity(false, false, resultEntity);
        }

        return saveAndMapResultEntity(resultEntity);
    }

    private DetailedResult saveAndMapResultEntity(ResultEntity resultEntity) {
        ResultEntity savedResultEntity = resultRepository.save(resultEntity);

        return modelMapper.mapResultEntityToDetailedResult(savedResultEntity);
    }

    private DetailedResult saveAndMapResultEntity(Boolean aiSelected, Boolean viewed, ResultEntity resultEntity) {
        ResultEntity savedResultEntity = resultRepository.save(resultEntity);

        return modelMapper.mapResultEntityToDetailedResult(aiSelected, viewed, savedResultEntity);
    }

    public void deleteResult(Long resultId) {
        ResultEntity entity = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
        resultRepository.delete(entity);
    }

    public ResultDataContent getPredictionDataFromResult(Long resultId) {
        ResultEntity entity = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
        return new ResultDataContent(ResultDataType.fromString(entity.getDataType()), entity.getData());
    }
}
