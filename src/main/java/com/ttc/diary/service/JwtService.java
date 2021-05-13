package com.ttc.diary.service;

import com.ttc.diary.model.response.JwtResponse;
import com.ttc.diary.model.request.LoginRequest;

public interface JwtService {

    JwtResponse getUserAndToken(LoginRequest request);
}
