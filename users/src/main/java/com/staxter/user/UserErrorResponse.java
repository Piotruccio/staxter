package com.staxter.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class UserErrorResponse {

    @NonNull
    private final String code;
    @NonNull
    private final String description;
}
