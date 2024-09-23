package com.example.backstudy24.controller;


import com.example.backstudy24.dto.request.post.CreatePostRequestDto;
import com.example.backstudy24.dto.request.post.UpdatePostRequestDto;
import com.example.backstudy24.dto.response.post.PostListResponseDto;
import com.example.backstudy24.dto.response.post.PostResponseDto;
import com.example.backstudy24.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponseDto viewPost(@PathVariable("id") Long id) {
        return postService.viewPost(id);
    }

    @GetMapping("/list")
    public List<PostListResponseDto> viewPostList() {
        return postService.viewPostList();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto requestDto) {
        postService.createPost(requestDto);
        return ResponseEntity.ok("게시물 생성 완료");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@RequestBody UpdatePostRequestDto requestDto, @PathVariable("id") Long id) {
        postService.updatePost(requestDto, id);
        return ResponseEntity.ok("게시물 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("게시물 삭제 완료");
    }
}
