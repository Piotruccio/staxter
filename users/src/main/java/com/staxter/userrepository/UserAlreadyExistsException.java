package com.staxter.userrepository;

import com.staxter.user.UserError;
import lombok.Data;

/**
 * Represents a type of user exception related to user already exists error
 * condition.
 */
@Data
public class UserAlreadyExistsException extends UserException {

    /**
     * Default constructor that creates a new instance of user already exists
     * exception.
     */
    public UserAlreadyExistsException() {
        super(UserError.USER_ALREADY_EXISTS);
    }
}
