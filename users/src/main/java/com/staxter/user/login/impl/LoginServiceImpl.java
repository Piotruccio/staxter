package com.staxter.user.login.impl;

import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserDto;
import com.staxter.user.login.LoginService;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.UserMapper;
import com.staxter.userrepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    LoginServiceImpl(@NotNull UserRepository userRepository,
                     @NotNull PasswordEncoder passwordEncoder, @NotNull UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public @NotNull UserDto loginUser(@NotNull LoginDto loginDto) throws NoSuchUserException {
        log.info("Handling loginUser command: {}", loginDto);

        final User user = userRepository.getUser(loginDto.getUserName());

        if (checkUserLogin(loginDto, user)) {
            log.info("Logged in user successfully: {}", user);

            return userMapper.mapToUserDto(user); // We're done, user authenticated
        }
        throw new InvalidUserLoginException();
    }

    private boolean checkUserLogin(@NotNull LoginDto loginDto, @NotNull User user) {
        return passwordEncoder.matches(loginDto.getPassword(), user.getHashedPassword());
    }
}
