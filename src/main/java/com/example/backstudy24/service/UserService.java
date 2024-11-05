package com.example.backstudy24.service;

import com.example.backstudy24.Error.ErrorCode;
import com.example.backstudy24.Error.requestError.NotFoundException;
import com.example.backstudy24.Error.requestError.UnAuthorizedException;
import com.example.backstudy24.dto.request.user.RequestUserLoginDto;
import com.example.backstudy24.dto.request.user.RequestUserSignUpDto;
import com.example.backstudy24.infra.entity.UserEntity;
import com.example.backstudy24.infra.enums.UserRole;
import com.example.backstudy24.infra.jpa.UserRepo;
import com.example.backstudy24.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(RequestUserSignUpDto dto, HttpServletResponse response) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UnAuthorizedException("S404", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION);
        }

        // 학번 중복 오류
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        UserEntity user = dto.toEntity();
        userRepository.save(user);
    }

    public void login(RequestUserLoginDto dto, HttpServletResponse response) {

        if (!userRepository.existsByEmail(dto.getEmail())) {
            throw new UnAuthorizedException("L401-1", ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("L401-2", ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        setTokenInHeader(dto.getEmail(), response);
    }

    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        jwtTokenProvider.validateRefreshToken(refreshToken);

        String newAT = jwtTokenProvider.reissueAT(refreshToken, response);
        jwtTokenProvider.setHeaderAccessToken(response, newAT);
    }

    public void setTokenInHeader(String email, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("5003", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION));

        UserRole role = user.getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), role);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), role);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }
}
