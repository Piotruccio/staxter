package com.staxter.user.authentication.web;

import com.staxter.user.UserError;
import com.staxter.user.authentication.AuthenticationService;
import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/userservice")
class AuthenticationController {

    private final AuthenticationService authenticationService;

    AuthenticationController(@NotNull AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @NotNull UserDto registerUser(@RequestBody @Valid RegistrationDto registrationDto)
            throws UserAlreadyExistsException {

        log.info("Handling registerUser command: {}", registrationDto);

        return authenticationService.registerUser(registrationDto);
    }

    @PostMapping("/login")
    @NotNull UserDto loginUser(@RequestBody @Valid LoginDto loginDto) throws NoSuchUserException {
        log.info("Handling loginUser command: {}", loginDto);

        return authenticationService.loginUser(loginDto);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    @NotNull ResponseEntity<UserError> handleUserAlreadyExistsException(
            @NotNull UserAlreadyExistsException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getUserError());
    }

    @ExceptionHandler({ NoSuchUserException.class })
    @NotNull ResponseEntity<UserError> handleNoSuchUserException(
            @NotNull NoSuchUserException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getUserError());
    }
}
