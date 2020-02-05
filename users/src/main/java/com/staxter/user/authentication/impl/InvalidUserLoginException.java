package com.staxter.user.authentication.impl;

import com.staxter.userrepository.NoSuchUserException;
import lombok.Data;

@Data
public class InvalidUserLoginException extends NoSuchUserException {

    private static final String ERROR_CODE = "INVALID_USER_LOGIN";
    private static final String ERROR_DESCRIPTION = "Invalid username/password";

    public InvalidUserLoginException() {
        super(ERROR_CODE, ERROR_DESCRIPTION);
    }
}
