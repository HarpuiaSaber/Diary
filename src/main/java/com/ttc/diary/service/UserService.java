package com.ttc.diary.service;

import com.ttc.diary.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(Long id);

    UserDto add(UserDto dto);
}
