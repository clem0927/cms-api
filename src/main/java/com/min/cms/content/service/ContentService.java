package com.min.cms.content.service;

import com.min.cms.account.entity.Account;
import com.min.cms.account.repository.AccountRepository;
import com.min.cms.content.dto.ContentRequestDto;
import com.min.cms.content.dto.ContentResponseDto;
import com.min.cms.content.entity.Content;
import com.min.cms.content.repository.ContentRepository;
import com.min.cms.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void create(ContentRequestDto dto, String username) {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("유저 없음", HttpStatus.NOT_FOUND));

        contentRepository.save(
                Content.of(dto.getTitle(), dto.getDescription(), account)
        );
    }

    @Transactional(readOnly = true)
    public Page<ContentResponseDto> findPage(Pageable pageable, String keyword) {

        Page<Content> page;

        if (keyword == null || keyword.isBlank()) {
            page = contentRepository.findAll(pageable);
        } else {
            page = contentRepository.findByTitleContaining(keyword, pageable);
        }

        return page.map(ContentResponseDto::from);
    }

    @Transactional
    public ContentResponseDto findById(Long id) {

        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new CustomException("콘텐츠 없음", HttpStatus.NOT_FOUND));

        content.increaseView();

        return ContentResponseDto.from(content);
    }

    @Transactional
    public void update(Long id, ContentRequestDto dto, String username) {

        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new CustomException("콘텐츠 없음", HttpStatus.NOT_FOUND));

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("유저 없음", HttpStatus.NOT_FOUND));

        boolean isAdmin = account.getRole().name().equals("ADMIN");

        if (!content.getCreatedBy().getId().equals(account.getId()) && !isAdmin) {
            throw new CustomException("권한 없음", HttpStatus.FORBIDDEN);
        }

        content.update(dto.getTitle(), dto.getDescription(), account);
    }

    @Transactional
    public void delete(Long id, String username) {

        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new CustomException("콘텐츠 없음", HttpStatus.NOT_FOUND));

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("유저 없음", HttpStatus.NOT_FOUND));

        boolean isAdmin = account.getRole().name().equals("ADMIN");

        if (!content.getCreatedBy().getId().equals(account.getId()) && !isAdmin) {
            throw new CustomException("권한 없음", HttpStatus.FORBIDDEN);
        }

        contentRepository.delete(content);
    }
}