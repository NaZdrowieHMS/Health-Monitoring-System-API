package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultCommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultCommentRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultCommentEntity;
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
public class ResultCommentService {

    private final ResultCommentRepository resultCommentRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultCommentService(ResultCommentRepository resultCommentRepository, RequestValidator validator,
                                ModelMapper modelMapper) {
        this.resultCommentRepository = resultCommentRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<Comment> getResultComments(Long resultId, Integer page, Integer size) {
        validator.validateResult(resultId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<CommentWithAuthorProjection> resultComments = resultCommentRepository.getResultCommentsWithAuthorByResultId(resultId, pageRequest).getContent();

        return resultComments.stream()
                .map(modelMapper::mapProjectionToComment)
                .collect(Collectors.toList());
    }

    public Comment getResultCommentById(Long commentId) {
        ResultCommentEntity entity = resultCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Result comment with id " + commentId + " does not exist"));
        return mapResultComment(entity);
    }

    public Comment createResultComment(ResultCommentRequest resultCommentRequest) {
        validator.validate(resultCommentRequest);

        LocalDateTime now = LocalDateTime.now();
        ResultCommentEntity resultCommentEntity = ResultCommentEntity.builder()
                .doctorId(resultCommentRequest.getDoctorId())
                .resultId(resultCommentRequest.getResultId())
                .content(resultCommentRequest.getContent())
                .createdDate(now)
                .modifiedDate(now).build();

        return saveAndMapResultComment(resultCommentEntity);
    }

    public Comment updateResultComment(CommentUpdateRequest resultCommentRequest) {
        ResultCommentEntity resultCommentEntity = resultCommentRepository.findById(resultCommentRequest.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Result comment with id " + resultCommentRequest.getCommentId() + " does not exist"));
        validator.validateCommentUpdateRequest(resultCommentRequest, resultCommentEntity.getDoctorId());

        updateField(Optional.ofNullable(resultCommentRequest.getContent()), resultCommentEntity::setContent);
        resultCommentEntity.setModifiedDate(LocalDateTime.now());

        return saveAndMapResultComment(resultCommentEntity);
    }

    public void deleteResultComment(Long commentId) {
        ResultCommentEntity entity = resultCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Result comment with id " + commentId + " does not exist"));
        resultCommentRepository.delete(entity);
    }

    private Comment mapResultComment(ResultCommentEntity resultCommentEntity) {
        CommentWithAuthorProjection resultCommentProjection = resultCommentRepository.getResultCommentWithAuthorByCommentId(resultCommentEntity.getId()).orElse(null);
        return modelMapper.mapProjectionToComment(resultCommentProjection);
    }

    private Comment saveAndMapResultComment(ResultCommentEntity resultCommentEntity) {
        ResultCommentEntity savedResultCommentEntity = resultCommentRepository.save(resultCommentEntity);
        return mapResultComment(savedResultCommentEntity);
    }
}
