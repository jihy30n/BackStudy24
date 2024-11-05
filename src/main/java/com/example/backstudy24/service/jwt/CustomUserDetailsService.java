package com.example.backstudy24.service.jwt;

import com.example.backstudy24.infra.jpa.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    // username -> email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("<" +username + "> 의 이메일의 사용자는 존재하지 않습니다.")));
    }
}