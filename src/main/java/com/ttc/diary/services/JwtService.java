package com.ttc.diary.services;

import com.ttc.diary.models.JwtResponse;
import com.ttc.diary.models.LoginRequest;

public interface JwtService {

    JwtResponse getUserandToken(LoginRequest request);
}
