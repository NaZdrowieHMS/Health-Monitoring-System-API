package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.domain.exception.response.ErrorResponse;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.LoginRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.UserRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.User;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.UserInfo;
import agh.edu.pl.healthmonitoringsystem.domain.service.DoctorService;
import agh.edu.pl.healthmonitoringsystem.domain.service.UserService;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.PAGE_SIZE_PARAM;
import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.START_INDEX_PARAM;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Create a new user.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"User"}
    )
    public ResponseEntity<User> createUser(@Parameter(description = "User to be created request")
                                               @RequestBody @Valid UserRequest userRequest) {

        User user = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User login successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfo.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
            },
            tags = {"User"}
    )
    public ResponseEntity<UserInfo> loginUser(@Parameter(description = "User to be login request")
                                           @RequestBody @Valid LoginRequest userRequest) throws AuthenticationException {

        UserInfo user = userService.loginUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}

