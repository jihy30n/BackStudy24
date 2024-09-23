package com.example.backstudy24.service;


import com.example.backstudy24.dto.request.post.CreatePostRequestDto;
import com.example.backstudy24.dto.request.post.UpdatePostRequestDto;
import com.example.backstudy24.dto.response.post.PostListResponseDto;
import com.example.backstudy24.dto.response.post.PostResponseDto;
import com.example.backstudy24.infra.entity.PostEntity;
import com.example.backstudy24.infra.jpa.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepository;

    @Transactional(readOnly = true)
    public PostResponseDto viewPost(Long id) {
        PostEntity post = findPostById(id);
        return PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .userName(post.getUserName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> viewPostList() {
        return postRepository.findAll().stream()
                .map(post -> PostListResponseDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .createAt(post.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPost(CreatePostRequestDto requestDto) {
        PostEntity post = new PostEntity(
                requestDto.getPostTitle(),
                requestDto.getPostContent(),
                requestDto.getUserName()
        );
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(UpdatePostRequestDto requestDto, Long id) {
        PostEntity post = findPostById(id);
        post.updatePost(requestDto.getPostTitle(), requestDto.getPostContent());
    }

    @Transactional
    public void deletePost(Long id) {
        PostEntity post = findPostById(id);
        postRepository.delete(post);
    }

    private PostEntity findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }
}
