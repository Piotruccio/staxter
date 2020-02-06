package com.staxter.userrepository;

import com.staxter.user.UserError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * A common abstract implementation representing all user exceptions.
 * To be overridden by subclasses.
 */
@Data
@AllArgsConstructor
abstract class UserException extends Exception {

    @NonNull
    private final UserError userError;
}
