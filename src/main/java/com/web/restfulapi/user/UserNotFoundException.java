package com.web.restfulapi.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//2xx -> ok
//4xx -> Client error
//5xx -> Server error
@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
        log.info("UserNotFoundException");
    }
}
