package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;


import agh.edu.pl.healthmonitoringsystemapplication.database.SupabaseConnection;
import agh.edu.pl.healthmonitoringsystemapplication.database.SupabaseConnectionService;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SupabaseConnectionService connectionService;
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
            SupabaseConnection connection = connectionService.leaseConnection();
            connectionService.returnConnection(connection);
            log.info("System is ready.");
            return ResponseEntity.status(200).body(new ErrorResponse(HttpStatus.OK.value(),"Ready :)."));
        } catch (InterruptedException e) {
            return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Connection to database is not working."));
        }
//        boolean isDatabaseConnected = checkDatabaseConnection();
//        if (isDatabaseConnected) {
//            log.info("System is ready.");
//            return ResponseEntity.ok("Ready");
//        } else {
//            log.error("System is not ready: database connection failed.");
//            return ResponseEntity.status(500).body(new ErrorResponse("Not ready"));
//        }
    }
}
