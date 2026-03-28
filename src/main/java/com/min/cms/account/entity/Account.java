package com.min.cms.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //  생성 책임
    public static Account of(String username, String encodedPassword) {
        return Account.builder()
                .username(username)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}