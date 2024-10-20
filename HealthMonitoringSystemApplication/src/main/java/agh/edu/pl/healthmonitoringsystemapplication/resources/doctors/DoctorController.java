package agh.edu.pl.healthmonitoringsystemapplication.resources.doctors;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.services.DoctorService;
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
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping(params = { START_INDEX_PARAM, PAGE_SIZE_PARAM })
    @Operation(
            summary = "Get list of all doctors.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = DoctorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Doctors"}
    )
    public ResponseEntity<List<DoctorResponse>> getDoctors(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                             @Parameter(description = "Number of doctors per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize) {

        List<DoctorResponse> doctors = doctorService.getDoctors(startIndex, pageSize)
                .stream()
                .map(doctor -> DoctorResponse.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .surname(doctor.getSurname())
                        .email(doctor.getEmail())
                        .pesel(doctor.getPesel())
                        .pwz(doctor.getPwz())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}")
    @Operation(
            summary = "Get the specific doctor by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = DoctorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(),
            tags = {"Doctors"}
    )
    public ResponseEntity<DoctorResponse> getDoctorById(@Parameter(description = "Doctor ID") @PathVariable("doctorId") Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);

        DoctorResponse doctorResponse = DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .email(doctor.getEmail())
                .pesel(doctor.getPesel())
                .pwz(doctor.getPwz()).build();

        return ResponseEntity.ok(doctorResponse);
    }
}
