package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultUploadRequest;
import agh.edu.pl.healthmonitoringsystem.response.DetailedResult;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultOverview;
import agh.edu.pl.healthmonitoringsystem.domain.service.ResultService;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
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

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;

@RestController
@RequestMapping("/api/results")
@CrossOrigin
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    @Operation(
            summary = "Upload medical result for a specific patient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Medical result uploaded successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DetailedResult.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<DetailedResult> uploadResult(@Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId,
                                                       @Parameter(description = "Result upload request")
                                                       @RequestBody @Valid ResultUploadRequest resultRequest) {

        DetailedResult result = resultService.uploadResult(userId, resultRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{resultId}")
    @Operation(
            summary = "Delete a specific result",
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
            tags = {"Result"}
    )
    public ResponseEntity<Void> deleteResult(@Parameter(description = "Result ID") @PathVariable Long resultId) {

        resultService.deleteResult(resultId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/{resultId}/data")
    @Operation(
            summary = "Get a specific result data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResultDataContent.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<ResultDataContent> getPredictionDataFromResult(@Parameter(description = "Result ID") @PathVariable Long resultId) {

        ResultDataContent resultData = resultService.getPredictionDataFromResult(resultId);
        return ResponseEntity.ok(resultData);
    }

    @GetMapping
    @Operation(
            summary = "Get all results filtered by patient ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultOverview.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<List<ResultOverview>> getAllResultsByPatientId(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                                              @Parameter(description = "Number of results per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "10") @Max(100) Integer pageSize,
                                                                                              @Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId,
                                                                                              @Parameter(description = "Patient ID") @RequestParam Long patientId) throws IllegalAccessException {

        List<ResultOverview> results = resultService.getAllResultsByPatientId(userId, patientId, startIndex, pageSize);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{resultId}")
    @Operation(
            summary = "Get result by result ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DetailedResult.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<DetailedResult> getResultByResultId(@Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId,
                                                              @Parameter(description = "Result ID") @PathVariable Long resultId) throws IllegalAccessException {

        DetailedResult result = resultService.getResultByResultId(userId, resultId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/unviewed")
    @Operation(
            summary = "Get all unviewed results, currently available only for doctors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultOverview.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Result"}
    )
    public ResponseEntity<List<ResultOverview>> getAllUnviewedResults(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                         @Parameter(description = "Number of results per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "10") @Max(100) Integer pageSize,
                                                                         @Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId) throws IllegalAccessException {

        List<ResultOverview> results = resultService.getUnviewedResults(userId, startIndex, pageSize);
        return ResponseEntity.ok(results);
    }
}
