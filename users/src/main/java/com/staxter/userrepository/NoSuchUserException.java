package com.staxter.userrepository;

import com.staxter.user.UserError;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Represents a type of user exception related to no such user exists error
 * condition.
 */
@Data
public class NoSuchUserException extends UserException {

    /**
     * Default constructor that creates a new instance of user already
     * exists exception.
     */
    public NoSuchUserException() {
        super(UserError.NO_SUCH_USER);
    }

    /**
     * Creates a new instance of user exception with given user error type.
     * To be used by subclasses as needed.
     *
     * @param userError the user error type to create
     */
    protected NoSuchUserException(@NotNull UserError userError) {
        super(userError);
    }
}
