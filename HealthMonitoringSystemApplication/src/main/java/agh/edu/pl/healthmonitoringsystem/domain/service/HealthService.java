package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static agh.edu.pl.healthmonitoringsystem.domain.component.UpdateUtil.updateField;

@Service
public class HealthService {

    private final HealthRepository healthRepository;
    private final UserRepository userRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public HealthService(HealthRepository healthRepository, UserRepository userRepository, RequestValidator validator,
                         ModelMapper modelMapper) {
        this.healthRepository = healthRepository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public Comment createHealthComment(CommentRequest healthCommentRequest) {
        validator.validate(healthCommentRequest.getDoctorId(), healthCommentRequest.getPatientId());

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
        validator.validateCommentUpdateRequest(healthCommentRequest, healthCommentEntity.getDoctorId());

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

    public List<Comment> getAllHealthComments(Long userId, Long patientId, String filter, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("modifiedDate").descending());

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));

        if(patientId == null) {
            if(user.getRole().equals(Role.PATIENT)) return fetchAllHealthCommentsForPatient(userId, pageRequest);
            return getAllHealthCommentsForUser(userId, pageRequest);
        }
        validator.validateAccessToPatientData(user, patientId);

        return fetchHealthCommentsByRole(userId, user.getRole(), patientId, filter, pageRequest);
    }

    private List<Comment> fetchHealthCommentsByRole(Long userId, Role role, Long patientId, String filter, PageRequest pageRequest) {
        if (role.equals(Role.DOCTOR)) {
            if (filter == null) return fetchAllHealthCommentsForPatient(patientId, pageRequest);
            return switch (filter.toLowerCase()) {
                case "specific" -> getPatientHealthCommentsAuthoredBySpecificDoctor(userId, patientId, pageRequest);
                case "others" -> getPatientHealthCommentsAuthoredByOtherDoctors(userId, patientId, pageRequest);
                default -> throw new IllegalArgumentException("Invalid filter value. Use 'specific' or 'others'.");
            };
        }

        return fetchAllHealthCommentsForPatient(patientId, pageRequest);
    }

    private List<Comment> fetchAllHealthCommentsForPatient(Long patientId, PageRequest pageRequest) {
        return healthRepository.getHealthCommentsWithAuthorByPatientId(patientId, pageRequest).getContent().stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    private List<Comment> getAllHealthCommentsForUser(Long userId, PageRequest pageRequest) {
        validator.validateUser(userId);
        return healthRepository.getAllHealthCommentsWithAuthor(pageRequest).stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    public List<Comment> getPatientHealthCommentsAuthoredBySpecificDoctor(Long doctorId, Long patientId, PageRequest pageRequest) {
        validator.validatePatient(patientId);
        validator.validateDoctor(doctorId);
        List<CommentWithAuthorProjection> healthComments = healthRepository.getPatientHealthCommentsAuthoredBySpecificDoctor(doctorId, patientId, pageRequest).getContent();

        return healthComments.stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    public List<Comment> getPatientHealthCommentsAuthoredByOtherDoctors(Long doctorId, Long patientId, PageRequest pageRequest) {
        validator.validatePatient(patientId);
        validator.validateDoctor(doctorId);
        List<CommentWithAuthorProjection> healthComments = healthRepository.getPatientHealthCommentsAuthoredByOtherDoctors(doctorId, patientId, pageRequest).getContent();

        return healthComments.stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    private Comment mapHealthComment(HealthCommentEntity healthCommentEntity) {
        CommentWithAuthorProjection healthCommentWithAuthorProjection = healthRepository.getHealthCommentWithAuthorByCommentId(healthCommentEntity.getId()).orElse(null);
        return modelMapper.mapProjectionToComment(healthCommentWithAuthorProjection);
    }

    private Comment saveAndMapHealthComment(HealthCommentEntity healthCommentEntity) {
        HealthCommentEntity savedHealthCommentEntity = healthRepository.save(healthCommentEntity);
        return mapHealthComment(savedHealthCommentEntity);
    }
}