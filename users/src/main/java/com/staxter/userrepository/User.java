package com.staxter.userrepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
public class User {

    @NonNull
    private String id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String userName;

    // Exclude password wherever possible (shouldn't be exposed!)
    @ToString.Exclude
    private String plainTextPassword;

    @NonNull
    @ToString.Exclude
    private String hashedPassword;
}
