package com.staxter.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
class UsersApplication {

    /**
     * Starting point of the users application allowing to run it with
     * given arguments.
     *
     * @param args the arguments to run with
     */
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
