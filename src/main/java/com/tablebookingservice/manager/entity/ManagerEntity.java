package com.tablebookingservice.manager.entity;

import com.tablebookingservice.global.type.MemberType;
import com.tablebookingservice.global.entity.GlobalEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Security UserDetails 인터페이스 구현 -> 보안 관련 정보 제공
public class ManagerEntity extends GlobalEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한 반환
        // MemberType에 따라 권한 설정
        return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 정상 상태 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 정상 상태 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 정상 상태 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 정상 상태 반환
    }
}
