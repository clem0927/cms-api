package com.min.cms.content.repository;

import com.min.cms.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findByTitleContaining(String keyword, Pageable pageable);
}