package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.service.PredictionRequestService;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.service.FormService;
import agh.edu.pl.healthmonitoringsystem.domain.service.HealthService;
import agh.edu.pl.healthmonitoringsystem.domain.service.ReferralService;
import agh.edu.pl.healthmonitoringsystem.domain.service.ResultService;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
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

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientSpecificController {

    private final ReferralService referralService;
    private final HealthService healthService;
    private final FormService formService;
    private final PredictionRequestService predictionRequestService;

    public PatientSpecificController(ReferralService referralService, HealthService healthService,
                                     FormService formService, PredictionRequestService predictionRequestService) {
        this.referralService = referralService;
        this.healthService = healthService;
        this.formService = formService;
        this.predictionRequestService = predictionRequestService;
    }

//    @GetMapping(path = "/{patientId}/predictions")
//    @Operation(
//            summary = "Get list of predictions for a specific patient",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Successful operation",
//                            content = @Content(schema = @Schema(type = "array", implementation = PredictionSummary.class))),
//                    @ApiResponse(responseCode = "400", description = "Invalid request",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "404", description = "Not found",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "500", description = "Server error",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
//            },
//            tags = {"Patient"}
//    )
//    public ResponseEntity<List<PredictionSummary>> getPatientPredictions(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
//                                                                         @Parameter(description = "Number of predictions per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
//                                                                         @Parameter(description = "Patient ID") @PathVariable Long patientId) {
//
//        List<PredictionSummary> patientPredictions = predictionRequestService.getPatientPredictions(patientId, startIndex, pageSize);
//        return ResponseEntity.ok(patientPredictions);
//    }
}

