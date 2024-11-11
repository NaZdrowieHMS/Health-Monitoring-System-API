package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientProjection;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final ReferralService referralService;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultService(ResultRepository resultRepository, ReferralService referralService, RequestValidator validator, ModelMapper modelMapper) {
        this.resultRepository = resultRepository;
        this.referralService = referralService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<Result> getPatientResultsByPatientId(Long patientId, Integer page, Integer size) {
        validator.validatePatient(patientId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        List<ResultEntity> results = resultRepository.getPatientResultsByPatientId(patientId, pageRequest).getContent();

        return results.stream()
                .map(modelMapper::mapResultEntityToResult)
                .collect(Collectors.toList());
    }

    public Result uploadResult(ResultUploadRequest resultRequest) {
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
        return saveAndMapResultEntity(resultEntity);
    }

    private Result saveAndMapResultEntity(ResultEntity resultEntity) {
        ResultEntity savedResultEntity = resultRepository.save(resultEntity);

        return modelMapper.mapResultEntityToResult(savedResultEntity);
    }

    public List<ResultForDoctorView> getDoctorPatientResultWithAiSelectedAndViewed(Long doctorId, Long patientId, Integer page, Integer size) {
        validator.validate(doctorId, patientId);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        List<ResultWithAiSelectedAndViewedProjection> results = resultRepository.getDoctorPatientResultWithAiSelectedAndViewed(doctorId, patientId, pageRequest).getContent();

        return results.stream()
                .map(modelMapper::mapProjectionToResultForDoctorView)
                .collect(Collectors.toList());
    }

    public List<ResultWithPatientData> getDoctorUnviewedResults(Long doctorId, Integer page, Integer size) {
        validator.validateDoctor(doctorId);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        List<ResultWithPatientProjection> results = resultRepository.getDoctorUnviewedResults(doctorId, pageRequest).getContent();

        return results.stream()
                .map(modelMapper::mapProjectionToResultWithPatientData)
                .collect(Collectors.toList());
    }

    public void deleteResult(Long resultId) {
        ResultEntity entity = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
        resultRepository.delete(entity);
    }
}
