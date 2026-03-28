package com.min.cms.content.controller;

import com.min.cms.content.dto.ContentRequestDto;
import com.min.cms.content.dto.ContentResponseDto;
import com.min.cms.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public String create(@RequestBody ContentRequestDto dto, Authentication auth) {
        contentService.create(dto, auth.getName());
        return "콘텐츠 생성 완료";
    }

    @GetMapping
    public Page<ContentResponseDto> list(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return contentService.findPage(pageable, keyword);
    }

    @GetMapping("/{id}")
    public ContentResponseDto detail(@PathVariable Long id) {
        return contentService.findById(id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody ContentRequestDto dto, Authentication auth) {
        contentService.update(id, dto, auth.getName());
        return "수정 완료";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        contentService.delete(id, auth.getName());
        return "삭제 완료";
    }
}