package com.example.backstudy24.dto.response.post;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostListResponseDto {

    private final Long postId;
    private final String postTitle;
    private final LocalDateTime createAt;

}
