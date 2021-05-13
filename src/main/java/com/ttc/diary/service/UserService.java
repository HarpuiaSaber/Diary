package com.ttc.diary.service;

import com.ttc.diary.model.dto.UserDto;
import com.ttc.diary.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    UserDetails loadUserById(Long id);

    Optional<UserPrincipal> getCurrentAuthenticatedUser();

    UserDto add(UserDto dto);
}
