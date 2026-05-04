package com.min.cms.security.service;

import com.min.cms.account.entity.Account;
import com.min.cms.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    //->아래 메서드의 UserDetails 객체는 DB에서 가져온 사용자 정보->이게 Authentication 객체에
    //담겨 "인증상태" 표현하는 객체됨
    @Override
    public UserDetails loadUserByUsername(String username) {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().name())
                .build();
    }
}