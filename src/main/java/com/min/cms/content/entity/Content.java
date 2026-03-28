package com.min.cms.content.entity;

import com.min.cms.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Account createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by")
    private Account lastModifiedBy;

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.viewCount = 0L;
    }

    @PreUpdate
    public void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    public static Content of(String title, String description, Account account) {
        return Content.builder()
                .title(title)
                .description(description)
                .createdBy(account)
                .lastModifiedBy(account)
                .viewCount(0L)
                .build();
    }

    public void update(String title, String description, Account account) {
        this.title = title;
        this.description = description;
        this.lastModifiedBy = account;
    }

    public void increaseView() {
        this.viewCount++;
    }
}