package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.service.DoctorPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DoctorPatientRelationController {
    private final DoctorPatientService doctorPatientService;

    public DoctorPatientRelationController(DoctorPatientService doctorPatientService) {
        this.doctorPatientService = doctorPatientService;
    }

    @PutMapping("/doctors/patients/relation")
    @Operation(
            summary = "Create a doctor patient relation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relation created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"DoctorPatient Relation"}
    )
    public ResponseEntity<Void> createRelation(@Parameter(description = "Doctor patient relation request") @RequestBody @Valid DoctorPatientRelationRequest request) {

        doctorPatientService.createRelation(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/doctors/{doctorId}/patients/{patientId}/relation")
    @Operation(
            summary = "Delete a doctor patient relation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relation deleted successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"DoctorPatient Relation"}
    )
    public ResponseEntity<Void> deleteRelation(@Parameter(description = "Doctor ID") @PathVariable Long doctorId,
                                               @Parameter(description = "Patient ID") @PathVariable Long patientId) {

        doctorPatientService.deleteRelation(doctorId, patientId);
        return ResponseEntity.ok().build();
    }
}
