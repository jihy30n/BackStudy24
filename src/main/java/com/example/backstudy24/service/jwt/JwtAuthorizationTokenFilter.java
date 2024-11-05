package com.example.backstudy24.service.jwt;

import com.example.backstudy24.Error.*;
import com.example.backstudy24.Error.requestError.ExpiredRefreshTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String path = request.getRequestURI();

        try {
            // 리프레시 토큰만 존재하고 경로가 "/reissue"인 경우 리프레시 토큰 검증 후 필터 통과
            if (accessToken == null && refreshToken != null && path.contains("/reissue")) {
                jwtTokenProvider.validateRefreshToken(refreshToken);
                filterChain.doFilter(request, response);
                return;
            }

            // 두 토큰이 모두 없는 경우 필터를 통과 (인증이 필요하지 않은 경로 처리)
            if (accessToken == null && refreshToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 액세스 토큰이 유효한 경우 SecurityContext에 인증 설정
            if (jwtTokenProvider.validateAccessToken(accessToken)) {
                setAuthentication(accessToken);
            }

        } catch (Exception e) {
            // 예외 발생 시, 해당 예외에 맞는 오류 코드를 반환
            ErrorJwtCode errorCode = mapExceptionToErrorCode(e);
            setResponse(response, errorCode);
            return;
        }

        // 모든 예외 처리가 끝난 후 필터 체인 호출
        filterChain.doFilter(request, response);
    }

    // 발생한 예외에 따른 ErrorJwtCode 반환
    private ErrorJwtCode mapExceptionToErrorCode(Exception e) {
        if (e instanceof ExpiredJwtException) {
            return ErrorJwtCode.EXPIRED_ACCESS_TOKEN;
        } else if (e instanceof MalformedJwtException) {
            return ErrorJwtCode.INVALID_JWT_FORMAT;
        } else if (e instanceof UnsupportedJwtException) {
            return ErrorJwtCode.UNSUPPORTED_JWT_TOKEN;
        } else if (e instanceof IllegalArgumentException) {
            return ErrorJwtCode.INVALID_VALUE;
        } else if (e instanceof ExpiredRefreshTokenException) {
            return ErrorJwtCode.EXPIRED_REFRESH_TOKEN;
        } else {
            e.printStackTrace(); // 예외 로그
            return ErrorJwtCode.RUNTIME_EXCEPTION;
        }
    }

    // 유효한 액세스 토큰을 기반으로 SecurityContext에 인증 설정

    private void setAuthentication(String token) throws BadRequestException {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 클라이언트에 에러 응답을 JSON 형태로 반환

    private void setResponse(HttpServletResponse response, ErrorJwtCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());

        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
