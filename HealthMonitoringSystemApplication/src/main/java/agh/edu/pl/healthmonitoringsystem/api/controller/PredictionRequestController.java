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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;

import java.util.List;


@RestController
@RequestMapping("/api/predictions/request")
@CrossOrigin
public class PredictionRequestController {
    private final PredictionRequestService predictionRequestService;

    public PredictionRequestController(PredictionRequestService predictionRequestService) {
        this.predictionRequestService = predictionRequestService;
    }

    @GetMapping
    @Operation(
            summary = "Get list of predictions made by doctor, optionally filtered by patient ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = PredictionSummary.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"AI Prediction"}
    )
    public ResponseEntity<List<PredictionSummary>> getAllDoctorPredictions(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                         @Parameter(description = "Number of predictions per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                                           @Parameter(description = "Doctor ID") @RequestHeader(name = "doctorId") Long doctorId,
                                                                           @Parameter(description = "Filter by Patient ID") @RequestParam(required = false) Long patientId) {

        List<PredictionSummary> patientPredictions = predictionRequestService.getPatientPredictions(doctorId, patientId, startIndex, pageSize);
        return ResponseEntity.ok(patientPredictions);
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