package com.example.backstudy24.dto.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePostRequestDto {

    private String postTitle;
    private String postContent;
    private String userName;
}
