package com.example.backstudy24.infra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostEntity extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String userName;

    public PostEntity(String title, String content, String userName) {
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
