package com.ttc.diary.services;

import com.ttc.diary.models.JwtResponse;
import com.ttc.diary.models.LoginRequest;

public interface JwtService {

    JwtResponse getUserAndToken(LoginRequest request);
}
