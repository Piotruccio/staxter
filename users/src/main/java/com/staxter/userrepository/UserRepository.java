package com.staxter.userrepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * An interface representing facade for user repository -- given by
 * preconditions.
 */
public interface UserRepository {

    /**
     * Creates a new user in the repository.
     *
     * @param user the user data to create user with
     * @return the user entity of the created user
     * @throws UserAlreadyExistsException when user with given data already
     *      exists
     */
    @NotNull User createUser(@Valid @NotNull User user) throws UserAlreadyExistsException;

    /**
     * Returns the user data for given user name.
     *
     * @param userName the user name to search user data
     * @return  the user entity of the existing user
     * @throws NoSuchUserException when no such user exists
     */
    @NotNull User getUser(@Valid @NotNull String userName) throws NoSuchUserException;
}
