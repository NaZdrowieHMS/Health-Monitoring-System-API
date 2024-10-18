package agh.edu.pl.healthmonitoringsystemapplication.resources.patients;

import agh.edu.pl.healthmonitoringsystemapplication.database.SupabaseConnection;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.models.Prediction;
import agh.edu.pl.healthmonitoringsystemapplication.services.DbConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@RestController
@RequestMapping("/api/patient")
@CrossOrigin
@Slf4j
public class PatientsController {
    private final DbConnectionService dbConnectionService;

    public PatientsController(DbConnectionService dbConnectionService) {
        this.dbConnectionService = dbConnectionService;
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Testing DB by getting patients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fetching patient went smoothly",
                            content = @Content(schema = @Schema(implementation = Prediction.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(),
            tags = {"Sonething"}
    )
    public ResponseEntity<String> testGetPatients(@PathVariable Long id) {
        String query = "SELECT * from patient where id = ?;";
        Statement statement = null;
        ResultSet result = null;
        SupabaseConnection connection = null;
        try {
            connection = dbConnectionService.getDbConnection().leaseConnection();

            result = connection.executeQuery(query, id);
            statement = result.getStatement();

            if (result.next()) {
                String name = result.getString("name");
                String surname = result.getString("surname");
                log.info("[DB_TEST] Received record: name = " + name + ", surname = " + surname);
            } else {
                log.info("[DB_TEST] No record found for id: " + id);
            }

            return ResponseEntity.ok("Cośtam przetestowane");

        } catch (SQLException e) {
            log.error(String.format("Database connection is not working. Exception: {%s}", e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Wywaliło się :C");

        } finally {
            try {
                // Close the ResultSet, PreparedStatement, and return the connection to the pool
                if (result != null && statement != null) {
                    connection.closeResultSetAndReturnConnection(result, statement);
                }
            } catch (SQLException e) {
                log.error("Error closing the ResultSet/Statement or returning connection: " + e.getMessage());
            }
        }
    }
}
