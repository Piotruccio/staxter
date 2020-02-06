package com.staxter.userrepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents a simple mapper implementation that is able to convert data
 * between data entity and dto classes.
 */
@Component
public class UserMapper {

    /**
     * Returns a new user entity with data converted from given registration
     * dto.
     *
     * @param registrationDto the registration dto to convert
     * @param passwordEncoder the encoder to encode user password
     * @return a new user entity with data converted from given registration dto
     */
    public @NotNull User mapToUser(@NotNull RegistrationDto registrationDto,
            @NotNull PasswordEncoder passwordEncoder) {

        return User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .userName(registrationDto.getUserName())
                // Do not store password as plain text!
                //.plainTextPassword(registrationDto.getPassword())
                .hashedPassword(passwordEncoder.encode(registrationDto.getPassword()))
                .build();
    }

    /**
     * Returns a new user dto instance with data converted from given user
     * entity.
     *
     * @param user the user data to convert
     * @return a new user dto instance with data converted from given user
     *      entity
     */
    public @NotNull UserDto mapToUserDto(@NotNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .build();
    }
}
