package com.staxter.userrepository;

import lombok.Data;

@Data
public class NoSuchUserException extends UserException {

    private static final String ERROR_CODE = "NO_SUCH_USER";
    private static final String ERROR_DESCRIPTION = "A user with the given username does not exist";

    public NoSuchUserException() {
        super(ERROR_CODE, ERROR_DESCRIPTION);
    }

    protected NoSuchUserException(String code, String description) {
        super(code, description);
    }
}
