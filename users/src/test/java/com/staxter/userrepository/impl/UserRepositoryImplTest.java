package com.staxter.userrepository.impl;

import com.staxter.user.TestBase;
import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryImplTest extends TestBase {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final User testUser = createTestUser();

    @Test
    public void shouldCreateNewUserAndReturnUserData() throws UserAlreadyExistsException {
        // given

        // when
        final User resultingUser = userRepository.createUser(testUser);

        // then
        assertEquals(testUser, resultingUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void shouldThrowUserAlreadyExistsExceptionForExisting()
            throws UserAlreadyExistsException {

        // given

        // when
        userRepository.createUser(testUser);
        userRepository.createUser(testUser);

        // then expected UserAlreadyExistsException
    }

    @Test
    public void shouldReturnExistingUserData() throws UserAlreadyExistsException,
            NoSuchUserException {

        // given
        final User createdUser = userRepository.createUser(testUser);

        // when
        final User resultingUser = userRepository.getUser(createdUser.getUserName());

        // then
        assertEquals(createdUser, resultingUser);
    }

    @Test(expected = NoSuchUserException.class)
    public void shouldThrowNoSuchUserExceptionForUnknownUserName() throws NoSuchUserException {
        // given

        // when
        userRepository.getUser(testUser.getUserName());

        // then expected NoSuchUserException
    }
}
