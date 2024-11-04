package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.domain.service.DoctorPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;


@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorPatientController {
    private final DoctorPatientService doctorPatientService;

    public DoctorPatientController(DoctorPatientService doctorPatientService) {
        this.doctorPatientService = doctorPatientService;
    }

    @GetMapping(path = "/{doctorId}/patients")
    @Operation(
            summary = "Get list of doctor's patients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<Patient>> getDoctorPatient(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                           @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                           @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<Patient> doctorPatients = doctorPatientService.getPatientsByDoctorId(doctorId, startIndex, pageSize);
        return ResponseEntity.ok(doctorPatients);
    }

    @GetMapping(path = "/{doctorId}/patients/{patientId}/results")
    @Operation(
            summary = "Get all patient results for a specific doctor view",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultForDoctorView.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<ResultForDoctorView>> getDoctorPatientResultWithAiSelectedAndViewed(@Parameter(description = "Doctor ID") @PathVariable Long doctorId,
                                                                                                   @Parameter(description = "Patient ID") @PathVariable Long patientId) {

        List<ResultForDoctorView> doctorPatientResults = doctorPatientService.getDoctorPatientResultWithAiSelectedAndViewed(doctorId, patientId);
        return ResponseEntity.ok(doctorPatientResults);
    }

    @GetMapping(path = "/{doctorId}/results/unviewed")
    @Operation(
            summary = "Get all patient results for a specific doctor view",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = ResultWithPatientData.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctor"}
    )
    public ResponseEntity<List<ResultWithPatientData>> getDoctorUnviewedResults(@Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<ResultWithPatientData> doctorPatientsResults = doctorPatientService.getDoctorUnviewedResults(doctorId);
        return ResponseEntity.ok(doctorPatientsResults);
    }
}
