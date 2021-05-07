package com.ttc.diary.services;

import com.ttc.diary.models.UserDetailDto;
import com.ttc.diary.models.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(Long id);

    UserDto add(UserDto dto);
}
