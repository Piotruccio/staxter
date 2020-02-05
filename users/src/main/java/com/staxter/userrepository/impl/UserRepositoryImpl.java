package com.staxter.userrepository.impl;

import com.staxter.userrepository.NoSuchUserException;
import com.staxter.userrepository.User;
import com.staxter.userrepository.UserAlreadyExistsException;
import com.staxter.userrepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple user repository implementation that holds all user registrations in
 * memory, within a map by username as ID.
 */
@Service
@Slf4j
class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public @NotNull User createUser(@Valid @NotNull final User user)
            throws UserAlreadyExistsException {

        final User existingUser = users.putIfAbsent(user.getUserName(), user);
        if (existingUser != null) {
            log.info("User already exists error; userName: " + user.getUserName());
            throw new UserAlreadyExistsException();
        }

        log.info("Created user: " + user);
        return user;
    }

    @Override
    public @NotNull User getUser(@Valid @NotNull String userName) throws NoSuchUserException {
        final User existingUser = users.get(userName);
        if (existingUser == null) {
            log.info("No such user exists error; userName: " + userName);
            throw new NoSuchUserException();
        }

        log.info("Found registered user; userName: " + existingUser.getUserName());
        return existingUser;
    }
}
