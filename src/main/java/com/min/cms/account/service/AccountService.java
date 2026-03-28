package com.min.cms.account.service;

import com.min.cms.account.dto.AccountDto;
import com.min.cms.account.entity.Account;
import com.min.cms.account.entity.Role;
import com.min.cms.account.repository.AccountRepository;
import com.min.cms.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(AccountDto dto) {

        if (accountRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new CustomException("이미 존재하는 유저", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Account account = Account.of(dto.getUsername(), encodedPassword);

        accountRepository.save(account);
    }
}