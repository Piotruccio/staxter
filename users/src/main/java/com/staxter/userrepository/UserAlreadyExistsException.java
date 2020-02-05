package com.staxter.userrepository;

import lombok.Data;

@Data
public class UserAlreadyExistsException extends UserException {

    private static final String ERROR_CODE = "USER_ALREADY_EXISTS";
    private static final String ERROR_DESCRIPTION = "A user with the given username already exists";

    public UserAlreadyExistsException() {
        super(ERROR_CODE, ERROR_DESCRIPTION);
    }
}
