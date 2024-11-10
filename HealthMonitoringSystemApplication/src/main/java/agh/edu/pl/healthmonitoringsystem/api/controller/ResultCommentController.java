package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultCommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.service.ResultCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;


@RestController
@RequestMapping("/api/results")
@CrossOrigin
public class ResultCommentController {
    private final ResultCommentService resultCommentService;

    public ResultCommentController(ResultCommentService resultCommentService) {
        this.resultCommentService = resultCommentService;
    }

    @GetMapping(path = "/{resultId}/comments")
    @Operation(
            summary = "Get list of comments with autor data for a specific result.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<List<Comment>> getResultComments(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                  @Parameter(description = "Number of result comments per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                                  @Parameter(description = "Result ID") @PathVariable Long resultId) {

        List<Comment> resultComments = resultCommentService.getResultComments(resultId, startIndex, pageSize);
        return ResponseEntity.ok(resultComments);
    }

    @PostMapping(value = "/comments")
    @Operation(
            summary = "Add comment for a specific result",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Result comment added successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result Comment"}
    )
    public ResponseEntity<Comment> createResultComment(@Parameter(description = "Result comment request")
                                                       @RequestBody @Valid ResultCommentRequest resultCommentRequest) {

        Comment resultComment = resultCommentService.createResultComment(resultCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultComment);
    }

    @PutMapping(value = "/comments")
    @Operation(
            summary = "Modify a specific result comment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result Comment"}
    )
    public ResponseEntity<Comment> updateResultComment(@Parameter(description = "Result comment update request")
                                                       @RequestBody @Valid CommentUpdateRequest resultCommentRequest) {

        Comment resultComment = resultCommentService.updateResultComment(resultCommentRequest);
        return ResponseEntity.ok(resultComment);
    }

    @GetMapping(path = "/comments/{commentId}")
    @Operation(
            summary = "Get a specific result comment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result Comment"}
    )
    public ResponseEntity<Comment> getResultComment(@Parameter(description = "Result comment ID") @PathVariable Long commentId) {

        Comment resultComment = resultCommentService.getResultCommentById(commentId);
        return ResponseEntity.ok(resultComment);
    }

    @DeleteMapping(path = "/comments/{commentId}")
    @Operation(
            summary = "Delete a specific result comment",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result Comment"}
    )
    public ResponseEntity<Void> deleteResultComment(@Parameter(description = "Result comment ID") @PathVariable Long commentId) {

        resultCommentService.deleteResultComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
