package com.example.backstudy24.dto.request.post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostRequestDto {

    private String postTitle;
    private String PostContent;

}
