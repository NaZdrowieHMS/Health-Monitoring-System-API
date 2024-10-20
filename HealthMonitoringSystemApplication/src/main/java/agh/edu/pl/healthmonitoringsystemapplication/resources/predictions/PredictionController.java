package agh.edu.pl.healthmonitoringsystemapplication.resources.predictions;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.services.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/predictions")
@CrossOrigin
public class PredictionController {
    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Predict breast cancer possibility with AI model.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Prediction successful",
                            content = @Content(schema = @Schema(implementation = PredictionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PredictionRequest.class))),
            tags = {"AI Prediction"}
    )
    public ResponseEntity<PredictionResponse> testPrediction(@Parameter(description = "Test prediction request")
                                                         @Valid @RequestBody PredictionRequest request) {
        PredictionResponse predictionResponse = predictionService.predict(request);

        return ResponseEntity.ok(predictionResponse);
    }
}
