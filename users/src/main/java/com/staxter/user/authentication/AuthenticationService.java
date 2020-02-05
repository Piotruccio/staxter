package com.staxter.user.authentication;

import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserDto;

import javax.validation.constraints.NotNull;

public interface AuthenticationService {

    @NotNull UserDto registerUser(@NotNull RegistrationDto registrationDto) throws UserAlreadyExistsException;

    @NotNull UserDto loginUser(@NotNull LoginDto loginDto) throws NoSuchUserException;
}
