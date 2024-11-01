package agh.edu.pl.healthmonitoringsystem.domain.service;


import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.validator.ResultRequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultAiSelectedRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultAiSelectedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ResultService {
    private final ResultAiSelectedRepository resultRepository;
    private final ResultRequestValidator resultRequestValidator;

    @Autowired
    public ResultService(ResultAiSelectedRepository resultRepository, ResultRequestValidator resultRequestValidator){
        this.resultRepository = resultRepository;
        this.resultRequestValidator = resultRequestValidator;
    }

    public void selectResult(ResultRequest resultRequest) {
        resultRequestValidator.validate(resultRequest);

        Optional<ResultAiSelectedEntity> entity = resultRepository.findByResultIdAndPatientIdAndDoctorId(
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
            resultRepository.save(resultAiSelectedEntity);
        }
    }

    public void unselectResult(ResultRequest resultRequest) {
        resultRequestValidator.validate(resultRequest);

        Optional<ResultAiSelectedEntity> entity = resultRepository.findByResultIdAndPatientIdAndDoctorId(
                resultRequest.getResultId(),
                resultRequest.getPatientId(),
                resultRequest.getDoctorId()
        );
        entity.ifPresent(resultRepository::delete);
    }
}
