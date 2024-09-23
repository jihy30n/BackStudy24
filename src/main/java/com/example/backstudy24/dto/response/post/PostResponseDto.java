package com.example.backstudy24.dto.response.post;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {

    private final Long postId;
    private final String postTitle;
    private final String postContent;
    private final String userName;

}
