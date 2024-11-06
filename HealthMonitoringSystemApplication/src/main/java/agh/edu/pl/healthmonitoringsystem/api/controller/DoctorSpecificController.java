package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Relation;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.domain.service.DoctorPatientService;
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
    private final ResultService resultService;

    public DoctorSpecificController(DoctorPatientService doctorPatientService, PatientService patientService, ResultService resultService) {
        this.doctorPatientService = doctorPatientService;
        this.patientService = patientService;
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
    public ResponseEntity<List<Patient>> getDoctorPatient(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                           @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                           @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<Patient> doctorPatients = patientService.getPatientsByDoctorId(doctorId, startIndex, pageSize);
        return ResponseEntity.ok(doctorPatients);
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

    @GetMapping(path = "/{doctorId}/patients/{patientId}/results")
    @Operation(
            summary = "Get patient results for a specific doctor view",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultForDoctorView.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<ResultForDoctorView>> getDoctorPatientResultWithAiSelectedAndViewed(@Parameter(description = "Doctor ID") @PathVariable Long doctorId,
                                                                                                   @Parameter(description = "Patient ID") @PathVariable Long patientId) {

        List<ResultForDoctorView> doctorPatientResults = resultService.getDoctorPatientResultWithAiSelectedAndViewed(doctorId, patientId);
        return ResponseEntity.ok(doctorPatientResults);
    }

    @GetMapping(path = "/{doctorId}/results/unviewed")
    @Operation(
            summary = "Get unviewed results for a specific doctor view",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultWithPatientData.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<ResultWithPatientData>> getDoctorUnviewedResults(@Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<ResultWithPatientData> doctorPatientsResults = resultService.getDoctorUnviewedResults(doctorId);
        return ResponseEntity.ok(doctorPatientsResults);
    }
}
