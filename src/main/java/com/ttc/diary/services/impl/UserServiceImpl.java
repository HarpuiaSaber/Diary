package com.ttc.diary.services.impl;

import com.ttc.diary.entities.User;
import com.ttc.diary.models.UserDto;
import com.ttc.diary.models.UserPrincipal;
import com.ttc.diary.repositories.UserRepository;
import com.ttc.diary.services.UserService;
import com.ttc.diary.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with id: " + id));

        UserPrincipal principal = new UserPrincipal(user.getUsername(), user.getPassword(), true, true,
                true, true, new ArrayList<>());
        principal.setId(user.getId());
        principal.setName(user.getName());
        principal.setAvatar(user.getAvatar());

        return principal;
    }

    @Override
    public UserDto add(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!!!");
        User user = new User();
        user.setId(null);
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordGenerator.getHashString(dto.getPassword()));
        user.setName(dto.getName());
        user.setAvatar("/user/avatar.jpg");
        userRepository.save(user);
        dto.setId(user.getId());
        return dto;
    }
}
