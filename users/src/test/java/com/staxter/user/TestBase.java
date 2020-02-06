package com.staxter.user;

import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.RegistrationDto;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserDto;

import javax.validation.constraints.NotNull;

/**
 * A common test implementation class to be extended by concrete test classes.
 */
public class TestBase {

    // Data represent single test user, common for all test cases

    public static final String TEST_ID = "dkhgokehio3er";
    public static final String TEST_FIRST_NAME = "Martin";
    public static final String TEST_LAST_NAME = "Schmidt";
    public static final String TEST_USER_NAME = "mschmidt";
    public static final String TEST_PASSWORD = "12345678";
    public static final String TEST_HASHED_PASSWORD = "$#@^%#^";

    // All factory methods share the same test user data

    protected @NotNull RegistrationDto createTestRegistrationDto() {
        return new RegistrationDto(
                TEST_FIRST_NAME, TEST_LAST_NAME, TEST_USER_NAME, TEST_PASSWORD);
    }

    protected @NotNull LoginDto createTestLoginDto() {
        return new LoginDto(TEST_USER_NAME, TEST_PASSWORD);
    }

    protected @NotNull UserDto createTestUserDto() {
        return UserDto.builder()
                .id(TEST_ID)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .userName(TEST_USER_NAME)
                .build();
    }

    protected @NotNull User createTestUser() {
        return User.builder()
                .id(TEST_ID)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .userName(TEST_USER_NAME)
                .hashedPassword(TEST_HASHED_PASSWORD)
                .build();
    }
}
