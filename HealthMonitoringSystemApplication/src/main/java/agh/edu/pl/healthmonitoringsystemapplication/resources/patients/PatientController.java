package agh.edu.pl.healthmonitoringsystemapplication.resources.patients;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static agh.edu.pl.healthmonitoringsystemapplication.resources.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystemapplication.resources.Constants.START_INDEX_PARAM;

@Slf4j
@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(params = { START_INDEX_PARAM, PAGE_SIZE_PARAM })
    @Operation(
            summary = "Get list of all patients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = PatientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Patient"}
    )
    public ResponseEntity<List<PatientResponse>> getPatients(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                             @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize) {

        List<PatientResponse> patients = patientService.getPatients(startIndex, pageSize)
                .stream()
                .map(patient -> PatientResponse.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .surname(patient.getSurname())
                        .email(patient.getEmail())
                        .pesel(patient.getPesel())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{patientId}")
    @Operation(
            summary = "Get the specific patient by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = PatientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(),
            tags = {"Patient"}
            )
    public ResponseEntity<PatientResponse> getPatientById(@Parameter(description = "Patient ID") @PathVariable("patientId") Long patientId) {
        Patient patient = patientService.getPatientById(patientId);

        PatientResponse patientResponse = PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .pesel(patient.getPesel()).build();

        return ResponseEntity.ok(patientResponse);
        }
}
