package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ResultRequest;
import agh.edu.pl.healthmonitoringsystem.domain.service.ResultService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/results/ai-selected")
@CrossOrigin
public class AiSelectedController {
    private final ResultService resultService;

    public AiSelectedController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PutMapping
    @Operation(
            summary = "Select result for AI Prediction.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result selected successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Result"}
    )
    public ResponseEntity<Void> selectResult(@Parameter(description = "Select result request") @RequestBody @Valid ResultRequest resultRequest) {

        resultService.selectResult(resultRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(
            summary = "Unselect result for AI Prediction.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result unselected successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Result"}
    )
    public ResponseEntity<Void> unselectResult(@Parameter(description = "Unselect result request") @RequestBody @Valid ResultRequest resultRequest) {

        resultService.unselectResult(resultRequest);
        return ResponseEntity.ok().build();
    }
}
