package com.staxter.user.login.web;

import com.staxter.user.UserErrorResponse;
import com.staxter.user.login.LoginService;
import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;
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
@RequestMapping("/userservice/login")
class LoginController {

    private final LoginService loginService;

    LoginController(@NotNull LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @NotNull UserDto loginUser(@RequestBody @Valid LoginDto loginDto) throws NoSuchUserException {
        log.info("Handling loginUser command: {}", loginDto);

        return loginService.loginUser(loginDto);
    }

    @ExceptionHandler({ NoSuchUserException.class })
    @NotNull ResponseEntity<UserErrorResponse> handleNoSuchUserException(
            @NotNull NoSuchUserException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toUserErrorResponse());
    }
}
