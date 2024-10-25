package agh.edu.pl.healthmonitoringsystemapplication.api.resources;


import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.DoctorPatientService;
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
import java.util.stream.Collectors;

import static agh.edu.pl.healthmonitoringsystemapplication.api.commons.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystemapplication.api.commons.Constants.START_INDEX_PARAM;


@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorPatientController {
    private final DoctorPatientService doctorPatientService;

    public DoctorPatientController(DoctorPatientService doctorPatientService) {
        this.doctorPatientService = doctorPatientService;
    }

    @GetMapping(path = "/{doctorId}/all-patients", params = { START_INDEX_PARAM, PAGE_SIZE_PARAM })
    @Operation(
            summary = "Get list of all doctor's patients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = PatientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"DoctorPatients"}
    )
    public ResponseEntity<List<PatientResponse>> getDoctorPatients(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                   @Parameter(description = "Number of patients per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                                   @Parameter(description = "Doctor ID") @PathVariable Long doctorId) {

        List<PatientResponse> doctorPatients = doctorPatientService.getPatientsByDoctorId(doctorId, startIndex, pageSize)
                .stream()
                .map(patient -> PatientResponse.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .surname(patient.getSurname())
                        .email(patient.getEmail())
                        .pesel(patient.getPesel())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorPatients);
    }
}
