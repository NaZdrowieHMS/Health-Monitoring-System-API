package agh.edu.pl.healthmonitoringsystem.domain.service;


import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultAiSelectedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultViewedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultAiSelectedEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultViewedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ResultOperationService {

    private final ResultAiSelectedRepository resultAiSelectedRepository;
    private final ResultViewedRepository resultViewedRepository;
    private final RequestValidator validator;

    @Autowired
    public ResultOperationService(ResultAiSelectedRepository resultAiSelectedRepository, ResultViewedRepository resultViewedRepository,
                                  RequestValidator validator){
        this.resultAiSelectedRepository = resultAiSelectedRepository;
        this.resultViewedRepository = resultViewedRepository;
        this.validator = validator;
    }

    public void selectResult(List<ResultRequest> resultRequests) {
        List<ResultAiSelectedEntity> resultAiSelectedEntities = resultRequests.stream()
                .peek(validator::validate)
                .filter(resultRequest -> resultAiSelectedRepository
                        .findByResultIdAndPatientIdAndDoctorId(
                                resultRequest.getResultId(),
                                resultRequest.getPatientId(),
                                resultRequest.getDoctorId()
                        ).isEmpty())
                .map(resultRequest -> ResultAiSelectedEntity.builder()
                        .resultId(resultRequest.getResultId())
                        .patientId(resultRequest.getPatientId())
                        .doctorId(resultRequest.getDoctorId())
                        .build())
                .collect(Collectors.toList());

        resultAiSelectedRepository.saveAll(resultAiSelectedEntities);
    }

    public void unselectResult(List<ResultRequest> resultRequests) {
        List<ResultAiSelectedEntity> resultAiUnselectedEntities = resultRequests.stream()
                .peek(validator::validate)
                .map(resultRequest -> resultAiSelectedRepository.findByResultIdAndPatientIdAndDoctorId(
                        resultRequest.getResultId(),
                        resultRequest.getPatientId(),
                        resultRequest.getDoctorId()
                ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        resultAiSelectedRepository.deleteAll(resultAiUnselectedEntities);
    }

    public void viewResult(ResultRequest resultRequest) {
        validator.validate(resultRequest);

        Optional<ResultViewedEntity> entity = resultViewedRepository.findByResultIdAndPatientIdAndDoctorId(
                resultRequest.getResultId(),
                resultRequest.getPatientId(),
                resultRequest.getDoctorId()
        );

        if (entity.isEmpty()) {
            ResultViewedEntity resultViewedEntity = ResultViewedEntity.builder()
                    .resultId(resultRequest.getResultId())
                    .patientId(resultRequest.getPatientId())
                    .doctorId(resultRequest.getDoctorId())
                    .build();
            resultViewedRepository.save(resultViewedEntity);
        }
    }
}
