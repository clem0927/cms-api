package com.min.cms.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 4, max = 12)
    private String password;
}