package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientHealthService {

    private final HealthRepository healthRepository;

    @Autowired
    public PatientHealthService(HealthRepository healthRepository) {
        this.healthRepository = healthRepository;
    }

    public List<HealthCommentWithAuthorProjection> getHealthCommentsByPatientId(Long patientId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HealthCommentWithAuthorProjection> healthCommentsPage = healthRepository.getHealthCommentsWithAutorByPatientId(patientId, pageRequest);
        return healthCommentsPage.getContent();
    }
}