package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.service.FormAnalysisService;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.FormRequest;
import agh.edu.pl.healthmonitoringsystem.domain.service.FormService;
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
@RequestMapping("/api/forms")
@CrossOrigin
public class FormController {
    private final FormService formService;
    private final FormAnalysisService formAnalysisService;

    public FormController(FormService formService, FormAnalysisService formAnalysisService) {
        this.formService = formService;
        this.formAnalysisService = formAnalysisService;
    }

    @GetMapping
    @Operation(
            summary = "Get list of health forms, optionally filtered by patient ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Form.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health Form"}
    )
    public ResponseEntity<List<Form>> getAllHealthForms(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                            @Parameter(description = "Number of forms per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                            @Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId,
                                                            @Parameter(description = "Filter by Patient ID") @RequestParam(required = false) Long patientId) {

        List<Form> patientHealthForms = formService.getAllHealthForms(userId, patientId, startIndex, pageSize);
        return ResponseEntity.ok(patientHealthForms);
    }

    @PostMapping
    @Operation(
            summary = "Upload health form for a specific patient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Health form successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Form.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health Form"}
    )
    public ResponseEntity<Form> uploadHealthForm(@Parameter(description = "Form upload request")
                                                       @RequestBody @Valid FormRequest formRequest) {

        Form healthform = formService.uploadHealthForm(formRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthform);
    }

    @GetMapping("/{formId}")
    @Operation(
            summary = "Get the specific form by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = Form.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health Form"}
    )
    public ResponseEntity<Form> getFormById(@Parameter(description = "Form ID") @PathVariable("formId") Long formId) {

        Form healthform = formService.getFormById(formId);
        return ResponseEntity.ok(healthform);
    }

//    @GetMapping("/{formId}/analysis")
//    @Operation(
//            summary = "Get last ai analysis for a specific form by form ID.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Successful operation",
//                            content = @Content(schema = @Schema(implementation = FormAiAnalysis.class))),
//                    @ApiResponse(responseCode = "404", description = "Not found",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "500", description = "Server error",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
//            },
//            tags = {"Health Form"}
//    )
//    public ResponseEntity<FormAiAnalysis> getFormLastAiAnalysisByFormId(@Parameter(description = "Form ID") @PathVariable("formId") Long formId,
//                                                                        @Parameter(description = "User ID") @RequestHeader(name = "userId") Long userId) {
//
//        FormAiAnalysis doctor = formAnalysisService.getFormLastAiAnalysisById(userId, formId);
//        return ResponseEntity.ok(doctor);
//    }

//    @PostMapping("/analysis")
//    @Operation(
//            summary = "Save a form ai analysis.",
//            responses = {
//                    @ApiResponse(responseCode = "201", description = "Ai form analysis created successfully",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FormAiAnalysis.class))),
//                    @ApiResponse(responseCode = "400", description = "Invalid request",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "500", description = "Server error",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
//            },
//            tags = {"Health Form"}
//    )
//    public ResponseEntity<FormAiAnalysis> saveFormAiAnalysis(@Parameter(description = "Ai form analysis to be saved request")
//                                                             @RequestBody @Valid AiFormAnalysisRequest aiFormAnalysisRequest) {
//
//        FormAiAnalysis doctor = formAnalysisService.saveFormAiAnalysis(aiFormAnalysisRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
//    }
}