package com.ttc.diary.controller;

import com.ttc.diary.model.JwtResponse;
import com.ttc.diary.model.LoginRequest;
import com.ttc.diary.model.UserDto;
import com.ttc.diary.service.JwtService;
import com.ttc.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public UserDto register(@RequestBody UserDto dto) {
        return userService.add(dto);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
      return jwtService.getUserAndToken(request);
    }
}
