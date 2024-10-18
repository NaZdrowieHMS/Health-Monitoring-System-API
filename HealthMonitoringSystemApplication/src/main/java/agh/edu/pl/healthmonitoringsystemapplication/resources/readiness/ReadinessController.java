package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;


import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.services.DbConnectionService;
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
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class ReadinessController {

    private final DbConnectionService dbConnectionService;

    public ReadinessController(DbConnectionService dbConnectionService) {
        this.dbConnectionService = dbConnectionService;
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
        try {
            dbConnectionService.isDbConnectionUsable();

            log.info("System is ready.");
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(),"Ready :)"));
        } catch (Exception e) {
            log.error(String.format("Database connection is not working. Exception: {%s}", e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
