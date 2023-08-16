package com.deadcow5.alarm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deadcow5.alarm.domain.User;
import com.deadcow5.alarm.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<User> createUser(@RequestBody String name) {
        User newUser = new User(name);
        userService.join(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
