package com.staxter.userrepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginDto {

    @Valid
    @NotNull
    private final String userName;

    @Valid
    @NotNull
    @ToString.Exclude
    private final String password;
}
