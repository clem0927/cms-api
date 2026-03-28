package com.min.cms.auth.controller;

import com.min.cms.account.dto.AccountDto;
import com.min.cms.account.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody AccountDto dto) {
        accountService.signup(dto);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountDto dto, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        return "로그인 성공";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 세션 삭제
        }

        return "로그아웃 성공";
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {

            return Map.of(
                    "message", "세션 없음"
            );
        }

        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }
}