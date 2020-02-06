package com.staxter.user.authentication.impl;

import com.staxter.user.TestBase;
import com.staxter.user.authentication.AuthenticationService;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.UserMapper;
import com.staxter.userrepository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Basic unit test coverage for the authentication service.
 */
public class AuthenticationServiceImplTest extends TestBase {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserMapper userMapper = mock(UserMapper.class);

    private final AuthenticationService authService = new AuthenticationServiceImpl(
            userRepository, passwordEncoder, userMapper);

    // Common test user data used in all/most of the test cases

    private final User testUser = createTestUser();
    private final UserDto testUserDto = createTestUserDto();

    @Before
    public void init() {
        initMocks(this);

        when(userMapper.mapToUser(any(), any())).thenReturn(testUser); // Common for all tests
        when(userMapper.mapToUserDto(any())).thenReturn(testUserDto);
    }

    @Test
    public void shouldRegisterUserAndReturnUserData() throws UserAlreadyExistsException {
        // given
        when(userRepository.createUser(any())).thenReturn(testUser);

        // when
        final UserDto resultingUserDto = authService.registerUser(createTestRegistrationDto());

        // then
        assertEquals(testUserDto, resultingUserDto);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void shouldThrowUserAlreadyExistsExceptionForUserAlreadyRegistered()
            throws UserAlreadyExistsException {

        // given
        when(userRepository.createUser(any())).thenThrow(UserAlreadyExistsException.class);

        // when
        authService.registerUser(createTestRegistrationDto());

        // then expected UserAlreadyExistsException
    }

    @Test
    public void shouldLoginUserAndReturnUserData() throws NoSuchUserException {
        // given
        when(userRepository.getUser(any())).thenReturn(testUser);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // when
        final UserDto resultingUserDto = authService.loginUser(createTestLoginDto());

        // then
        assertEquals(testUserDto, resultingUserDto);
    }

    @Test(expected = NoSuchUserException.class)
    public void shouldThrowNoSuchUserExceptionForUnknownUserName() throws NoSuchUserException {
        // given
        when(userRepository.getUser(any())).thenThrow(NoSuchUserException.class);

        // when
        authService.loginUser(createTestLoginDto());

        // then expected NoSuchUserException
    }

    @Test(expected = InvalidUserLoginException.class)
    public void shouldThrowInvalidUserLoginExceptionForInvalidUserPassword()
            throws NoSuchUserException {

        // given
        when(userRepository.getUser(any())).thenReturn(testUser);
        when(passwordEncoder.matches(any(), any())).thenReturn(false); // Non-match

        // when
        authService.loginUser(createTestLoginDto());

        // then expected InvalidUserLoginException
    }
}
