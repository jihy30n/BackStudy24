package com.example.backstudy24.dto.request.user;

import com.example.backstudy24.infra.entity.UserEntity;
import com.example.backstudy24.infra.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserSignUpDto {

    private String name;
    private String email;
    private String password;

    @Builder
    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .password(password)
                .userRole(UserRole.USER)
                .build();
    }

}
