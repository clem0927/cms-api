package com.min.cms.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CSRF 끄기 (Swagger 테스트 편하게)
                .csrf(csrf -> csrf.disable())

                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // Swagger 허용
                        .requestMatchers(
                                "/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**",
                                "/auth/**"
                        ).permitAll()

                        // 나머지는 인증 필요
                        .anyRequest().authenticated()
                )
                //h2용 iframe허용
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )

                // 기본 로그인 폼 끄기
                .formLogin(form -> form.disable())

                // HTTP Basic도 끄기 (불필요)
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //  로그인 핵심 (AuthenticationManager)
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}