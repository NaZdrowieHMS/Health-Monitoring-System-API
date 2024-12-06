package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.service.PredictionRequestService;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/predictions/request")
@CrossOrigin
public class PredictionRequestController {
    private final PredictionRequestService predictionRequestService;

    public PredictionRequestController(PredictionRequestService predictionRequestService) {
        this.predictionRequestService = predictionRequestService;
    }

    @GetMapping
    public ResponseEntity<PredictionSummary> test() {
        return ResponseEntity.status(HttpStatus.CREATED).body(predictionRequestService.getTest());
    }

    @PostMapping
    @Operation(
            summary = "Create a new prediction request.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Prediction request created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PredictionSummary.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"AI Prediction"}
    )
    public ResponseEntity<PredictionSummary> createPredictionRequest(@Parameter(description = "Prediction Summary to be created request")
                                               @RequestBody @Valid PredictionSummaryRequest predictionSummaryRequest) {

        PredictionSummary predictionSummary = predictionRequestService.createPredictionRequest(predictionSummaryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(predictionSummary);
    }

    @PutMapping
    @Operation(
            summary = "Update a specific prediction request.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Prediction request updated successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PredictionSummary.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"AI Prediction"}
    )
    public ResponseEntity<Void> updatePredictionRequest(@Parameter(description = "Prediction Summary to be updated request")
                                                                     @RequestBody @Valid PredictionSummaryUpdateRequest predictionSummaryRequest) {

        predictionRequestService.updatePredictionRequest(predictionSummaryRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{requestId}")
    @Operation(
            summary = "Get the specific prediction request summary by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = PredictionSummary.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"AI Prediction"}
    )
    public ResponseEntity<PredictionSummary> getPredictionSummaryRequestById(@Parameter(description = "Request ID") @PathVariable("requestId") Long requestId) {

        PredictionSummary predictionSummary = predictionRequestService.getPredictionSummaryRequestById(requestId);
        return ResponseEntity.ok(predictionSummary);
    }
}