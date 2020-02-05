package com.staxter.userrepository;

import com.staxter.user.UserErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
abstract class UserException extends Exception {

    @NonNull
    private final String code;
    @NonNull
    private final String description;

    public @NotNull UserErrorResponse toUserErrorResponse() {
        return UserErrorResponse.builder()
                .code(code).description(description).build();
    }
}
