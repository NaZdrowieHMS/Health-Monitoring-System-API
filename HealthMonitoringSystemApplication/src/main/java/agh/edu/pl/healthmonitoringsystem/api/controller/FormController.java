package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.service.FormAnalysisService;
import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.response.AiFormAnalysis;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping("/{formId}/analysis")
    @Operation(
            summary = "Get the ai analysis for a specific form by form ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = AiFormAnalysis.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Health Form"}
    )
    public ResponseEntity<AiFormAnalysis> getFormAiAnalysisByFormId(@Parameter(description = "Form ID") @PathVariable("formId") Long formId) {

        AiFormAnalysis doctor = formAnalysisService.getFormAiAnalysisById(formId);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/analysis")
    @Operation(
            summary = "Save a form ai analysis.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ai form analysis created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AiFormAnalysis.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Health Form"}
    )
    public ResponseEntity<AiFormAnalysis> saveFormAiAnalysis(@Parameter(description = "Ai form analysis to be saved request")
                                                             @RequestBody @Valid AiFormAnalysisRequest aiFormAnalysisRequest) {

        AiFormAnalysis doctor = formAnalysisService.saveFormAiAnalysis(aiFormAnalysisRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }
}