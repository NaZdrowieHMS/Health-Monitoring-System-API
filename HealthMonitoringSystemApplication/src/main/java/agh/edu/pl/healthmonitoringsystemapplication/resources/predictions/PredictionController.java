package agh.edu.pl.healthmonitoringsystemapplication.resources.predictions;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.ErrorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.models.Prediction;
import agh.edu.pl.healthmonitoringsystemapplication.services.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/predictions")
@CrossOrigin
@Slf4j
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
                            content = @Content(schema = @Schema(implementation = Prediction.class))),
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
    public ResponseEntity<Prediction> testPrediction(@Parameter(description = "Test prediction request")
                                                         @Valid @RequestBody PredictionRequest request) {
        Prediction prediction = predictionService.predict(request);

        return ResponseEntity.ok(prediction);
    }
}
