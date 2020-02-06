package com.staxter.userrepository;

import com.staxter.user.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Basic unit test coverage for the user mapper implementation class.
 */
public class UserMapperTest extends TestBase {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserMapper userMapper = new UserMapper();
    private final User testUser = createTestUser();

    @Before
    public void init() { initMocks(this); }

    @Test
    public void shouldMapRegistrationDtoToUser() {
        // given
        when(passwordEncoder.encode(any())).thenReturn(TEST_HASHED_PASSWORD);

        // when
        final User resultingUser = userMapper.mapToUser(createTestRegistrationDto(),
                passwordEncoder);

        // then
        assertUserEquals(testUser, resultingUser);
    }

    @Test
    public void shouldMapUserToUserDto() {
        // given
        final UserDto testUserDto = createTestUserDto();

        // when
        final UserDto resultingUserDto = userMapper.mapToUserDto(testUser);

        // then
        assertEquals(testUserDto, resultingUserDto);
    }

    private void assertUserEquals(User expected, User actual) {
        // Special handling of id due it's auto generated value, can't compare
        assertNotNull(actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getUserName(), actual.getUserName());

        // Special handling of plain text password since we skip it
        assertNull(actual.getPlainTextPassword());

        // Special handling of hashed password due it's auto hashed, check not null
        assertNotNull(actual.getHashedPassword());
    }
}
