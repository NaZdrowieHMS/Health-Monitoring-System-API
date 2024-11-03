package agh.edu.pl.healthmonitoringsystem.domain.service;


import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultAiSelectedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultViewedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultAiSelectedEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultViewedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    public void selectResult(ResultRequest resultRequest) {
        validator.validate(resultRequest);

        Optional<ResultAiSelectedEntity> entity = resultAiSelectedRepository.findByResultIdAndPatientIdAndDoctorId(
                resultRequest.getResultId(),
                resultRequest.getPatientId(),
                resultRequest.getDoctorId()
        );

        if (entity.isEmpty()) {
            ResultAiSelectedEntity resultAiSelectedEntity = ResultAiSelectedEntity.builder()
                    .resultId(resultRequest.getResultId())
                    .patientId(resultRequest.getPatientId())
                    .doctorId(resultRequest.getDoctorId())
                    .build();
            resultAiSelectedRepository.save(resultAiSelectedEntity);
        }
    }

    public void unselectResult(ResultRequest resultRequest) {
        validator.validate(resultRequest);

        Optional<ResultAiSelectedEntity> entity = resultAiSelectedRepository.findByResultIdAndPatientIdAndDoctorId(
                resultRequest.getResultId(),
                resultRequest.getPatientId(),
                resultRequest.getDoctorId()
        );
        entity.ifPresent(resultAiSelectedRepository::delete);
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
