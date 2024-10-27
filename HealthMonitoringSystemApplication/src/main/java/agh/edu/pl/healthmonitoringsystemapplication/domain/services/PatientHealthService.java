package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.domain.components.ModelMapper;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.HealthComment;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientHealthService {

    private final HealthRepository healthRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientHealthService(HealthRepository healthRepository, ModelMapper modelMapper) {
        this.healthRepository = healthRepository;
        this.modelMapper = modelMapper;
    }

    public List<HealthComment> getHealthCommentsByPatientId(Long patientId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<HealthCommentWithAuthorProjection> healthComments = healthRepository.getHealthCommentsWithAutorByPatientId(patientId, pageRequest).getContent();

        return healthComments.stream()
                .map(modelMapper::mapProjectionToHealth)
                .collect(Collectors.toList());
    }
}