package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PatientRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;

@Slf4j
@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(
            summary = "Get list of all patients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Patient"}
    )
    public ResponseEntity<List<Patient>> getPatients(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                     @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize) {

        List<Patient> patients = patientService.getPatients(startIndex, pageSize);
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    @Operation(
            summary = "Create a new patient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Patient created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Patient"}
    )
    public ResponseEntity<Patient> createPatient(@Parameter(description = "Patient to be created request")
                                                       @RequestBody @Valid PatientRequest patientRequest) {

        Patient patient = patientService.createPatient(patientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @GetMapping("/{patientId}")
    @Operation(
            summary = "Get the specific patient by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = Patient.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(),
            tags = {"Patients"}
            )
    public ResponseEntity<Patient> getPatientById(@Parameter(description = "Patient ID") @PathVariable("patientId") Long patientId) {

        Patient patient = patientService.getPatientById(patientId);
        return ResponseEntity.ok(patient);
        }
}
