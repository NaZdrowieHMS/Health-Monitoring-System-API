package agh.edu.pl.healthmonitoringsystem.domain.services;

import agh.edu.pl.healthmonitoringsystem.domain.components.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientResultService {

    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientResultService(ResultRepository resultRepository, ModelMapper modelMapper) {
        this.resultRepository = resultRepository;
        this.modelMapper = modelMapper;
    }

    public List<Result> getPatientResultsByPatientId(Long patientId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ResultEntity> results = resultRepository.getPatientResultsByPatientId(patientId, pageRequest).getContent();

        return results.stream()
                .map(modelMapper::mapResultEntityToResult)
                .collect(Collectors.toList());
    }
}
