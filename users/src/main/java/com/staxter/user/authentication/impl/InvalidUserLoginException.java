package com.staxter.user.authentication.impl;

import com.staxter.userrepository.NoSuchUserException;
import com.staxter.user.UserError;
import lombok.Data;

/**
 * Represents a type of user exception related to invalid user login error
 * condition.
 */
@Data
public class InvalidUserLoginException extends NoSuchUserException {

    /**
     * Default constructor that creates a new instance of invalid user login
     * exception.
     */
    public InvalidUserLoginException() {
        super(UserError.INVALID_USER_LOGIN);
    }
}
