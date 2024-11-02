package agh.edu.pl.healthmonitoringsystem.api.controller;


import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DeleteRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.service.ReferralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/referrals")
@CrossOrigin
public class ReferralController {

    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("/{referralId}")
    @Operation(
            summary = "Get the specific referral by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = Referral.class))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(),
            tags = {"Referral"}
    )
    public ResponseEntity<Referral> getReferralById(@Parameter(description = "Referral ID") @PathVariable("referralId") Long referralId) {

        Referral referral = referralService.getReferralById(referralId);
        return ResponseEntity.ok(referral);
    }

    @PostMapping
    @Operation(
            summary = "Create a new referral",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Referral created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Referral.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Referral"}
    )
    public ResponseEntity<Referral> createReferral(@Parameter(description = "Referral to be created request")
                                                   @RequestBody @Valid ReferralRequest referralRequest) {

        Referral referral = referralService.createReferral(referralRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(referral);
    }


    @DeleteMapping
    @Operation(
            summary = "Delete referral by referral Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result unselected successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"Referral"}
    )
    public ResponseEntity<Void> deleteReferral(@Parameter(description = "Unselect result request") @RequestBody @Valid DeleteRequest referralDeleteRequest) {

        referralService.deleteReferral(referralDeleteRequest);
        return ResponseEntity.ok().build();
    }
}

