package com.ttc.diary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttc.diary.model.response.JwtResponse;
import com.ttc.diary.model.request.LoginRequest;
import com.ttc.diary.model.dto.UserDto;
import com.ttc.diary.model.request.GridParam;
import com.ttc.diary.service.JwtService;
import com.ttc.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
    public UserDto register(@RequestBody @Valid UserDto dto) {
        return userService.add(dto);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid LoginRequest request) {
      return jwtService.getUserAndToken(request);
    }

    @GetMapping
    public GridParam getDecodeParam(@RequestParam String param){
        try {
            String decodedParam = URLDecoder.decode(param, StandardCharsets.UTF_8.toString());
            GridParam gridParam = new ObjectMapper().readValue(decodedParam, GridParam.class);
            return gridParam;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
