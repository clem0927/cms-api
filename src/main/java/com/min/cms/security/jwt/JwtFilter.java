package com.min.cms.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
//    [클라이언트]
//    Authorization: Bearer JWT
//
//        ↓
//
//                [JwtFilter]
//            1. 토큰 꺼냄
//            2. 검증
//            3. username, roles 추출
//            4. Authentication 생성
//            5. SecurityContext 저장
//
//        ↓
//
//                [Controller]
//
//           → 이미 로그인된 상태
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);//토큰 꺼내기

        if (token != null && jwtProvider.validateToken(token)) {//권한 및 만료시간검증

            // ⭐ claims 추출
            Claims claims = jwtProvider.parseClaims(token);

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            // ⭐ roles → 권한 객체 변환
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities // ⭐ 여기 바뀜
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}