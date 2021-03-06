package com.ttc.diary.services.impl;

import com.ttc.diary.components.jwt.JwtTokenProvider;
import com.ttc.diary.models.JwtResponse;
import com.ttc.diary.models.LoginRequest;
import com.ttc.diary.models.UserDetailDto;
import com.ttc.diary.models.UserPrincipal;
import com.ttc.diary.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtResponse getUserandToken(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setId(principal.getId());
        userDetailDto.setName(principal.getName());
        userDetailDto.setUsername(principal.getUsername());
        userDetailDto.setAvatar(principal.getAvatar());

        String jwt = jwtTokenProvider.generateToken(principal.getId());
        return new JwtResponse(userDetailDto, jwt);
    }
}
