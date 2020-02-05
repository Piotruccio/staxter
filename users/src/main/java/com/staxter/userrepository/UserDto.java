package com.staxter.userrepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    @NonNull
    private final String id;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @NonNull
    private final String userName;
}
