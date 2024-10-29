package agh.edu.pl.healthmonitoringsystem.api.resources;

import agh.edu.pl.healthmonitoringsystem.domain.exceptions.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.services.PatientReferralService;
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

import static agh.edu.pl.healthmonitoringsystem.api.commons.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.commons.Constants.START_INDEX_PARAM;


@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientReferralController {
    private final PatientReferralService patientReferralService;

    public PatientReferralController(PatientReferralService patientReferralService) {
        this.patientReferralService = patientReferralService;
    }

    @GetMapping(path = "/{patientId}/referrals")
    @Operation(
            summary = "Get list of referrals with comment for a specific patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(type = "array", implementation = Referral.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =  @Schema(implementation = ErrorResponse.class))),
            },
            tags = {"PatientReferral"}
    )
    public ResponseEntity<List<Referral>> getPatientHealthComments(@Parameter(description = "Start index") @RequestParam(name = START_INDEX_PARAM, required = false, defaultValue = "0") @Min(0) Integer startIndex,
                                                                   @Parameter(description = "Number of referrals per page") @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = "50") @Max(500) Integer pageSize,
                                                                   @Parameter(description = "PatientEntity ID") @PathVariable Long patientId) {

        List<Referral> patientReferrals = patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize);
        return ResponseEntity.ok(patientReferrals);
    }
}