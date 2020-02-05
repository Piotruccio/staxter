package com.staxter.user.registration.web;

import com.staxter.user.UserErrorResponse;
import com.staxter.user.registration.RegistrationService;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.UserAlreadyExistsException;
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
@RequestMapping("/userservice/register")
class RegistrationController {

    private final RegistrationService registrationService;

    RegistrationController(@NotNull RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    @NotNull UserDto registerUser(@RequestBody @Valid RegistrationDto registrationDto)
            throws UserAlreadyExistsException {

        log.info("Handling registerUser command: {}", registrationDto);

        return registrationService.registerUser(registrationDto);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    @NotNull ResponseEntity<UserErrorResponse> handleUserAlreadyExistsException(
            @NotNull UserAlreadyExistsException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toUserErrorResponse());
    }
}
