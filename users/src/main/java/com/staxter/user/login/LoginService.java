package com.staxter.user.login;

import com.staxter.userrepository.UserDto;
import com.staxter.userrepository.LoginDto;
import com.staxter.userrepository.NoSuchUserException;

import javax.validation.constraints.NotNull;

public interface LoginService {

    @NotNull UserDto loginUser(@NotNull LoginDto loginDto) throws NoSuchUserException;
}
