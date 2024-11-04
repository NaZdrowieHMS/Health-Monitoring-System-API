package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.service.HealthService;
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
@RequestMapping("/api")
@CrossOrigin
public class HealthController {
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping(path = "/patients/{patientId}/health")
    @Operation(
            summary = "Get list of health comment with autor data for a specific patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Patient"}
    )
    public ResponseEntity<List<Comment>> getPatientHealthComments(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                  @Parameter(description = "Number of health comments per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                                  @Parameter(description = "Patient ID") @PathVariable Long patientId) {

        List<Comment> patientHealthComments = healthService.getHealthCommentsByPatientId(patientId, startIndex, pageSize);
        return ResponseEntity.ok(patientHealthComments);
    }

    @PostMapping(path = "/health")
    @Operation(
            summary = "Add health comment for a specific patient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health"}
    )
    public ResponseEntity<Comment> createHealthComment(@Parameter(description = "Health comment request")
                                                           @RequestBody @Valid CommentRequest healthCommentRequest) {

        Comment healthComment = healthService.createHealthComment(healthCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthComment);
    }

    @PutMapping(path = "/health")
    @Operation(
            summary = "Modify a specific health comment",
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
            tags = {"Health"}
    )
    public ResponseEntity<Comment> updateHealthComment(@Parameter(description = "Health comment update request")
                                                           @RequestBody @Valid CommentUpdateRequest healthCommentRequest) {

        Comment healthComment = healthService.updateHealthComment(healthCommentRequest);
        return ResponseEntity.ok(healthComment);
    }

    @GetMapping(path = "/health/{commentId}")
    @Operation(
            summary = "Get a specific health comment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health"}
    )
    public ResponseEntity<Comment> getHealthComment(@Parameter(description = "Health comment ID") @PathVariable Long commentId) {

        Comment healthComment = healthService.getHealthCommentById(commentId);
        return ResponseEntity.ok(healthComment);
    }

    @DeleteMapping(path = "/health/{commentId}")
    @Operation(
            summary = "Delete a specific health comment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health"}
    )
    public ResponseEntity<Void> deleteHealthComment(@Parameter(description = "Health comment ID") @PathVariable Long commentId) {

        healthService.deleteHealthComment(commentId);
        return ResponseEntity.ok().build();
    }
}