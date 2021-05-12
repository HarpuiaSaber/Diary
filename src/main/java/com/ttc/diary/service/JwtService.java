package com.ttc.diary.service;

import com.ttc.diary.model.JwtResponse;
import com.ttc.diary.model.LoginRequest;

public interface JwtService {

    JwtResponse getUserAndToken(LoginRequest request);
}
