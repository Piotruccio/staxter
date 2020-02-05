package com.staxter.userrepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
public class User {

    @NonNull
    @EqualsAndHashCode.Exclude
    private String id;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String firstName;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String lastName;

    @NonNull
    private String userName;

    // Exclude password wherever possible (shouldn't be exposed!)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String plainTextPassword;

    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String hashedPassword;
}
