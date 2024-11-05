package com.example.backstudy24.controller;

import com.example.backstudy24.dto.request.user.RequestUserLoginDto;
import com.example.backstudy24.dto.request.user.RequestUserSignUpDto;
import com.example.backstudy24.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RequestUserSignUpDto dto, HttpServletResponse response) {
        userService.signUp(dto, response);
        return ResponseEntity.ok().body("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestUserLoginDto dto, HttpServletResponse response) {
        userService.login(dto, response);
        return ResponseEntity.ok().body("로그인 성공!");
    }

    @GetMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        userService.reissueToken(request, response);
        return ResponseEntity.ok().body("토큰 재발급 성공!");
    }

}
