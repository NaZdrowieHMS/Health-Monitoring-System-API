package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.component.ReadinessCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@CrossOrigin
@Slf4j
public class ReadinessController {

    private final ReadinessCheck readinessCheck;

    public ReadinessController(ReadinessCheck readinessCheck) {
        this.readinessCheck = readinessCheck;
    }

    @GetMapping("/health")
    @Operation(
            summary = "Check if ai health monitoring system is ready.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Healthy",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema =  @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Not healthy",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema =  @Schema(implementation = String.class))),
            },
            tags = {"Health Check"}
    )
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Healthy");
    }

    @GetMapping(value = "/readiness")
    @Operation(
            summary = "Check if health monitoring system is ready.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ready",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Not ready",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"Readiness Check"}
    )
    public ResponseEntity<ErrorResponse> readinessCheck(){
        if (readinessCheck.checkIfReady()){
            log.info("System is ready.");
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(),"Ready :)"));
        } else {
            log.error("System is not ready. Database connection is not working.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "System is not ready."));
        }
    }
}
