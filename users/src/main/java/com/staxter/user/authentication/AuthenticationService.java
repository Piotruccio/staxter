package com.staxter.user.authentication;

import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserDto;

import javax.validation.constraints.NotNull;

/**
 * Represents authentication service facade with support for registering/logging
 * in user.
 */
public interface AuthenticationService {

    /**
     * Register user with given user registration data.
     *
     * @param registrationDto the registration dto
     * @return
     * @throws UserAlreadyExistsException when user with given user name is
     *      already registered
     */
    @NotNull UserDto registerUser(@NotNull RegistrationDto registrationDto) throws UserAlreadyExistsException;

    /**
     * Login user with given login data.
     *
     * @param loginDto the login dto
     * @return
     * @throws NoSuchUserException when no such user is registered
     */
    @NotNull UserDto loginUser(@NotNull LoginDto loginDto) throws NoSuchUserException;
}
