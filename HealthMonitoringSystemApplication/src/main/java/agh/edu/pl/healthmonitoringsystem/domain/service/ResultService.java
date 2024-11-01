package agh.edu.pl.healthmonitoringsystem.domain.service;


import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.validator.ResultRequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultAiSelectedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultViewedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultAiSelectedEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultViewedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ResultService {
    private final ResultAiSelectedRepository resultAiSelectedRepository;
    private final ResultViewedRepository resultViewedRepository;
    private final ResultRequestValidator resultRequestValidator;

    @Autowired
    public ResultService(ResultAiSelectedRepository resultAiSelectedRepository, ResultViewedRepository resultViewedRepository,
                         ResultRequestValidator resultRequestValidator){
        this.resultAiSelectedRepository = resultAiSelectedRepository;
        this.resultViewedRepository = resultViewedRepository;
        this.resultRequestValidator = resultRequestValidator;
    }

    public void selectResult(ResultRequest resultRequest) {
        resultRequestValidator.validate(resultRequest);

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
        resultRequestValidator.validate(resultRequest);

        Optional<ResultAiSelectedEntity> entity = resultAiSelectedRepository.findByResultIdAndPatientIdAndDoctorId(
                resultRequest.getResultId(),
                resultRequest.getPatientId(),
                resultRequest.getDoctorId()
        );
        entity.ifPresent(resultAiSelectedRepository::delete);
    }

    public void viewResult(ResultRequest resultRequest) {
        resultRequestValidator.validate(resultRequest);

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
