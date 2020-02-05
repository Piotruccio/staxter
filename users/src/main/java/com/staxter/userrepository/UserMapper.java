package com.staxter.userrepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Component
public class UserMapper {

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

    public @NotNull UserDto mapToUserDto(@NotNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .build();
    }
}
