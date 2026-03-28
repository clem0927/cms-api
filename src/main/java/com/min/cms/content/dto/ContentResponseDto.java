package com.min.cms.content.dto;

import com.min.cms.content.entity.Content;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentResponseDto {

    private Long id;
    private String title;
    private String description;
    private Long viewCount;
    private String username;
    private LocalDateTime createdDate;

    public static ContentResponseDto from(Content content) {
        ContentResponseDto dto = new ContentResponseDto();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setDescription(content.getDescription());
        dto.setViewCount(content.getViewCount());
        dto.setUsername(content.getCreatedBy().getUsername());
        dto.setCreatedDate(content.getCreatedDate());
        return dto;
    }
}