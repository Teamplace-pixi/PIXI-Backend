package teamplace.pixi.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "is_sub", nullable = false)
    private boolean isSub;

    @CreatedDate
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "roll_id", nullable = false)
    private int rollId;

    @Column(name = "profile_id", nullable = false)
    private int profileId;

    @Column(name = "ai_cnt", nullable = false)
    private int aiCnt;


    @Builder
    public User(String loginId, String password, String email, String nickname,
                Boolean isSub, Integer rollId, Integer profileId, Integer aiCnt) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.isSub = isSub;
        this.rollId = rollId;
        this.profileId = profileId;
        this.aiCnt = aiCnt;
    }

    // 닉네임 변경
    public User updateNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    // 구독 여부 변경
    public User updateSubscription(boolean isSub) {
        this.isSub = isSub;
        return this;
    }

    // 역할 ID 변경
    public User updateRollId(int rollId) {
        this.rollId = rollId;
        return this;
    }

    // 프로필 사진 ID 변경
    public User updateProfileId(int profileId) {
        this.profileId = profileId;
        return this;
    }

    // AI 사용 횟수 1 감소 (최소값 0)
    public User decreaseAiCnt() {
        if (this.aiCnt > 0) {
            this.aiCnt--;
        }
        return this;
    }

    // AI 사용 횟수 5로 리셋
    public User resetAiCnt() {
        this.aiCnt = 5;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public String getPassword() {
        return password;
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