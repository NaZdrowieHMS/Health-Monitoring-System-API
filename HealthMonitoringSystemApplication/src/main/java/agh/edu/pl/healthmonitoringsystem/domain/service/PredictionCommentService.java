package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PredictionCommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionCommentRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionCommentEntity;
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
public class PredictionCommentService {

    private final PredictionCommentRepository predictionCommentRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PredictionCommentService(PredictionCommentRepository predictionCommentRepository, RequestValidator validator,
                                ModelMapper modelMapper) {
        this.predictionCommentRepository = predictionCommentRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<Comment> getPredictionComments(Long predictionId, Integer page, Integer size) {
        validator.validatePredictionSummary(predictionId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<CommentWithAuthorProjection> resultComments = predictionCommentRepository.getPredictionCommentsWithAuthorByPredictionId(predictionId, pageRequest).getContent();

        return resultComments.stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    public Comment createPredictionComment(PredictionCommentRequest predictionCommentRequest) {
        validator.validate(predictionCommentRequest);

        LocalDateTime now = LocalDateTime.now();
        AiPredictionCommentEntity aiPredictionCommentEntity = AiPredictionCommentEntity.builder()
                .doctorId(predictionCommentRequest.getDoctorId())
                .predictionSummaryId(predictionCommentRequest.getPredictionId())
                .content(predictionCommentRequest.getContent())
                .createdDate(now)
                .modifiedDate(now).build();

        return saveAndMapPredictionComment(aiPredictionCommentEntity);
    }

    public Comment updatePredictionComment(CommentUpdateRequest predictionCommentRequest) {
        AiPredictionCommentEntity predictionCommentEntity = predictionCommentRepository.findById(predictionCommentRequest.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Prediction comment with id " + predictionCommentRequest.getCommentId() + " does not exist"));
        validator.validateCommentUpdateRequest(predictionCommentRequest, predictionCommentEntity.getDoctorId());

        updateField(Optional.ofNullable(predictionCommentRequest.getContent()), predictionCommentEntity::setContent);
        predictionCommentEntity.setModifiedDate(LocalDateTime.now());

        return saveAndMapPredictionComment(predictionCommentEntity);
    }

    public Comment getPredictionCommentById(Long commentId) {
        AiPredictionCommentEntity entity = predictionCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Prediction comment with id " + commentId + " does not exist"));
        return mapPredictionComment(entity);
    }

    public void deletePredictionComment(Long commentId) {
        AiPredictionCommentEntity entity = predictionCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Prediction comment with id " + commentId + " does not exist"));
        predictionCommentRepository.delete(entity);
    }

    private Comment mapPredictionComment(AiPredictionCommentEntity predictionCommentEntity) {
        CommentWithAuthorProjection resultCommentProjection = predictionCommentRepository.getPredictionCommentWithAuthorByCommentId(predictionCommentEntity.getId()).orElse(null);
        return modelMapper.mapProjectionToComment(resultCommentProjection);
    }

    private Comment saveAndMapPredictionComment(AiPredictionCommentEntity predictionCommentEntity) {
            AiPredictionCommentEntity savedPredictionCommentEntity = predictionCommentRepository.save(predictionCommentEntity);
        return mapPredictionComment(savedPredictionCommentEntity);
    }
}
