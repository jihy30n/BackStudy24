package com.example.backstudy24.dto.response.post;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {

    private final Long postId;
    private final String postTitle;
    private final LocalDateTime createAt;

}
