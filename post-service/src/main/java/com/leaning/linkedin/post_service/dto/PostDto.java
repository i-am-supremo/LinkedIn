package com.leaning.linkedin.post_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
}
