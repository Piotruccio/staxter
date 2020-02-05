package com.staxter.user.registration.impl;

import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.user.registration.RegistrationService;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserMapper;
import com.staxter.userrepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    RegistrationServiceImpl(@NotNull UserRepository userRepository,
                            @NotNull PasswordEncoder passwordEncoder, @NotNull UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public @NotNull UserDto registerUser(@NotNull RegistrationDto registrationDto)
            throws UserAlreadyExistsException {

        log.info("Handling registerUser command: {}", registrationDto);

        final User user = userRepository.createUser(userMapper.mapToUser(registrationDto, passwordEncoder));
        log.info("Created user successfully: {}", user);

        return userMapper.mapToUserDto(user);
    }
}
