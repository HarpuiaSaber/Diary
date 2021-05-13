package com.ttc.diary.service.impl;

import com.ttc.diary.exception.UserIdNotFoundException;
import com.ttc.diary.model.entity.User;
import com.ttc.diary.model.dto.UserDto;
import com.ttc.diary.model.UserPrincipal;
import com.ttc.diary.repository.UserRepository;
import com.ttc.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        UserPrincipal principal = new UserPrincipal(user.getUsername(), user.getPassword(), true, true,
                true, true, authorities);
        principal.setId(user.getId());
        principal.setName(user.getName());
        principal.setAvatar(user.getAvatar());

        return principal;
    }

    @Override
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIdNotFoundException("Not found user with id: " + id));

        UserPrincipal principal = new UserPrincipal(user.getUsername(), user.getPassword(), true, true,
                true, true, new ArrayList<>());
        principal.setId(user.getId());
        principal.setName(user.getName());
        principal.setAvatar(user.getAvatar());

        return principal;
    }

    @Override
    public Optional<UserPrincipal> getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        Object obj = authentication.getPrincipal();
        if (obj instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) authentication.getPrincipal());
        }
        return Optional.empty();
    }


    @Override
    public UserDto add(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!!!");
        User user = new User();
        user.setId(null);
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setAvatar("/user/avatar.jpg");
        userRepository.save(user);
        dto.setId(user.getId());
        return dto;
    }
}
