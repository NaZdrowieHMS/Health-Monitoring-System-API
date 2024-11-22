package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.*;
import agh.edu.pl.healthmonitoringsystem.domain.service.DoctorPatientService;
import agh.edu.pl.healthmonitoringsystem.domain.service.HealthService;
import agh.edu.pl.healthmonitoringsystem.domain.service.PatientService;
import agh.edu.pl.healthmonitoringsystem.domain.service.ResultService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;


@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorSpecificController {
    private final DoctorPatientService doctorPatientService;
    private final PatientService patientService;
    private final HealthService healthService;
    private final ResultService resultService;

    public DoctorSpecificController(DoctorPatientService doctorPatientService, PatientService patientService, HealthService healthService, ResultService resultService) {
        this.doctorPatientService = doctorPatientService;
        this.patientService = patientService;
        this.healthService = healthService;
        this.resultService = resultService;
    }

    @GetMapping(path = "/{doctorId}/patients")
    @Operation(
            summary = "Get list of doctor's patients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<Patient>> getDoctorPatients(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                           @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                           @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<Patient> doctorPatients = patientService.getPatientsByDoctorId(doctorId, startIndex, pageSize);
        return ResponseEntity.ok(doctorPatients);
    }

    @GetMapping(path = "/{doctorId}/patients/unassigned")
    @Operation(
            summary = "Get list of patients not assigned to the doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<Patient>> getDoctorNewPatients(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                           @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                           @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<Patient> unassignedPatients = patientService.getUnassignedPatientsByDoctorId(doctorId, startIndex, pageSize);
        return ResponseEntity.ok(unassignedPatients );
    }

    @PutMapping("/patients/relation")
    @Operation(
            summary = "Create a doctor patient relation.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Relation created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Relation.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Relation"}
    )
    public ResponseEntity<Relation> createRelation(@Parameter(description = "Doctor patient relation request")
                                                       @RequestBody @Valid DoctorPatientRelationRequest request) {

        Relation relation = doctorPatientService.createRelation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(relation);
    }

    @DeleteMapping("/{doctorId}/patients/{patientId}/relation")
    @Operation(
            summary = "Delete a doctor patient relation.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Relation deleted successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Relation"}
    )
    public ResponseEntity<Void> deleteRelation(@Parameter(description = "Doctor ID") @PathVariable Long doctorId,
                                               @Parameter(description = "Patient ID") @PathVariable Long patientId) {

        doctorPatientService.deleteRelation(doctorId, patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/{doctorId}/patient/{patientId}/health")
    @Operation(
            summary = "Get list of health comment with author data for a specific patient, created by given doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )

    public ResponseEntity<List<Comment>> getPatientHealthComments(
            @Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
            @Parameter(description = "Number of health comments per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Patient ID") @PathVariable Long patientId,
            @Parameter(description = "Filter type: 'specific' (authored by the doctor) or 'others' (authored by other doctors)")
            @RequestParam(name = "filter") String filter) {

        List<Comment> patientHealthComments = switch (filter.toLowerCase()) {
            case "specific" -> healthService.getPatientHealthCommentsAuthoredBySpecificDoctor(doctorId, patientId, startIndex, pageSize);
            case "others" -> healthService.getPatientHealthCommentsAuthoredByOtherDoctors(doctorId, patientId, startIndex, pageSize);
            default -> throw new IllegalArgumentException("Invalid filter value. Use 'specific' or 'others'.");
        };

        return ResponseEntity.ok(patientHealthComments);
    }
}
