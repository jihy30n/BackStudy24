package com.example.backstudy24.service.jwt;

import static com.example.backstudy24.infra.enums.UserRole.ADMIN;
import static com.example.backstudy24.infra.enums.UserRole.USER;

import com.example.backstudy24.infra.entity.UserEntity;
import com.example.backstudy24.infra.enums.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if (user.getUserRole() == ADMIN) {
            authorityList.add(getAuthorities(ADMIN));
        }
        if (user.getUserRole() == USER) {
            authorityList.add(getAuthorities(USER));
        }

        return authorityList;
    }


    private GrantedAuthority getAuthorities(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole);
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //식별자
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
