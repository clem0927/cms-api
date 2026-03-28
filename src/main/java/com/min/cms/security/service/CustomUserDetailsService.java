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