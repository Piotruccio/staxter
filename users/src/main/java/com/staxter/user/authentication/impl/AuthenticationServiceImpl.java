package com.staxter.user.authentication.impl;

import com.staxter.user.authentication.AuthenticationService;
import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.UserMapper;
import com.staxter.userrepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    AuthenticationServiceImpl(@NotNull UserRepository userRepository,
                              @NotNull PasswordEncoder passwordEncoder, @NotNull UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public @NotNull UserDto registerUser(@NotNull RegistrationDto registrationDto)
            throws UserAlreadyExistsException {

        log.info("Handling registerUser command: {}", registrationDto);

        final User createdUser = userRepository.createUser(userMapper.mapToUser(
                registrationDto, passwordEncoder));

        log.info("Created user successfully: {}", createdUser);

        return userMapper.mapToUserDto(createdUser);
    }

    @Override
    public @NotNull UserDto loginUser(@NotNull LoginDto loginDto) throws NoSuchUserException {
        log.info("Handling loginUser command: {}", loginDto);

        final User existingUser = userRepository.getUser(loginDto.getUserName());

        if (checkUserLogin(loginDto, existingUser)) {
            log.info("Logged in user successfully: {}", existingUser);

            return userMapper.mapToUserDto(existingUser); // We're done, user authenticated
        }
        throw new InvalidUserLoginException();
    }

    private boolean checkUserLogin(@NotNull LoginDto loginDto, @NotNull User user) {
        return passwordEncoder.matches(loginDto.getPassword(), user.getHashedPassword());
    }
}
