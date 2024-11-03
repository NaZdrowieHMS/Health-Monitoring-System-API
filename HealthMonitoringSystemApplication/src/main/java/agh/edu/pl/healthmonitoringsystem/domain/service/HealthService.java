package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.validator.HealthCommentRequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static agh.edu.pl.healthmonitoringsystem.domain.component.UpdateUtil.updateField;

@Service
public class HealthService {

    private final HealthRepository healthRepository;
    private final HealthCommentRequestValidator healthCommentRequestValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public HealthService(HealthRepository healthRepository, HealthCommentRequestValidator healthCommentRequestValidator,
                         ModelMapper modelMapper) {
        this.healthRepository = healthRepository;
        this.healthCommentRequestValidator = healthCommentRequestValidator;
        this.modelMapper = modelMapper;
    }

    public Comment createHealthComment(CommentRequest healthCommentRequest) {
        healthCommentRequestValidator.validate(healthCommentRequest);

        LocalDateTime now = LocalDateTime.now();
        HealthCommentEntity healthCommentEntity = HealthCommentEntity.builder()
                .doctorId(healthCommentRequest.getDoctorId())
                .patientId(healthCommentRequest.getPatientId())
                .content(healthCommentRequest.getContent())
                .createdDate(now)
                .modifiedDate(now).build();

        return saveAndMapHealthComment(healthCommentEntity);
    }

    public Comment updateHealthComment(CommentUpdateRequest healthCommentRequest) {
        HealthCommentEntity healthCommentEntity = healthRepository.findById(healthCommentRequest.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Health comment with id " + healthCommentRequest.getCommentId() + " does not exist"));
        healthCommentRequestValidator.validateUpdateRequest(healthCommentRequest, healthCommentEntity);

        updateField(Optional.ofNullable(healthCommentRequest.getContent()), healthCommentEntity::setContent);
        healthCommentEntity.setModifiedDate(LocalDateTime.now());

        return saveAndMapHealthComment(healthCommentEntity);
    }

    public Comment getHealthCommentById(Long commentId) {
        HealthCommentEntity healthCommentEntity = healthRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Health comment with id " + commentId + " does not exist"));
        return mapHealthComment(healthCommentEntity);
    }

    public void deleteHealthComment(Long commentId) {
        HealthCommentEntity entity = healthRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Health comment with id " + commentId + " does not exist"));
        healthRepository.delete(entity);
    }

    public List<Comment> getHealthCommentsByPatientId(Long patientId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<HealthCommentWithAuthorProjection> healthComments = healthRepository.getHealthCommentsWithAutorByPatientId(patientId, pageRequest).getContent();

        return healthComments.stream()
                .map(modelMapper::mapProjectionToHealth)
                .collect(Collectors.toList());
    }

    private Comment mapHealthComment(HealthCommentEntity healthCommentEntity) {
        HealthCommentWithAuthorProjection healthCommentProjection = healthRepository.getHealthCommentWithAutorByPatientId(healthCommentEntity.getId());
        return modelMapper.mapProjectionToHealth(healthCommentProjection);
    }

    private Comment saveAndMapHealthComment(HealthCommentEntity healthCommentEntity) {
        HealthCommentEntity savedHealthCommentEntity = healthRepository.save(healthCommentEntity);
        return mapHealthComment(savedHealthCommentEntity);
    }
}