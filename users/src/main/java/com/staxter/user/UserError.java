package com.staxter.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

/**
 * Represents all types od user error conditions when registering/logging in.
 */
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum UserError {

    /**
     * Represents user already exists error condition.
     */
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS",
            "A user with the given username already exists"),
    /**
     * Represents no cuch user exists error condition.
     */
    NO_SUCH_USER("NO_SUCH_USER",
            "A user with the given username does not exist"),
    /**
     * Represents invalid user login error condition.
     */
    INVALID_USER_LOGIN("INVALID_USER_LOGIN",
            "Invalid username/password");

    UserError(@NotNull String code, @NotNull String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Returns the code of this user error.
     *
     * @return the string code of this user error
     */
    public @NotNull String getCode() { return code; }

    /**
     * Returns the description of this user error.
     *
     * @return the description of this user error
     */
    public @NotNull String getDescription() { return description; }

    private final String code;
    private final String description;
}
