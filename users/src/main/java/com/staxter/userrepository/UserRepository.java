package com.staxter.userrepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserRepository {

    @NotNull User createUser(@Valid @NotNull User user) throws UserAlreadyExistsException;

    @NotNull User getUser(@Valid @NotNull String userName) throws NoSuchUserException;
}
