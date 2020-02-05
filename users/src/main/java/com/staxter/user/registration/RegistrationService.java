package com.staxter.user.registration;

import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.UserAlreadyExistsException;

import javax.validation.constraints.NotNull;

public interface RegistrationService {

    @NotNull UserDto registerUser(@NotNull RegistrationDto registrationDto)
            throws UserAlreadyExistsException;
}
