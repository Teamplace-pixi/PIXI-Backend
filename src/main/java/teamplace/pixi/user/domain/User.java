package teamplace.pixi.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import teamplace.pixi.board.domain.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @Column(name = "address")
    private String address;

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

    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Builder
    public User(String loginId, String password, String email, String nickname, String address,
                boolean isSub, Integer rollId, Integer profileId, Integer aiCnt, String auth) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
        this.isSub = isSub;
        this.rollId = rollId;
        this.profileId = profileId;
        this.aiCnt = aiCnt;
    }

    // Id 변경
    public User updateLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    // 비밀번호 변경
    public User updatePassword(String password) {
        this.password = password;
        return this;
    }

    // 닉네임 변경
    public User updateNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    // 주소 변경
    public User updateAddress(String address) {
        this.address = address;
        return this;
    }

    // 구독 여부 변경
    public User updateSubscription(boolean isSub) {
        this.isSub = isSub;
        return this;
    }

    public boolean isUserSubscribed(User user) {
        return user.getSubscriptionEndDate() != null &&
                user.getSubscriptionEndDate().isAfter(LocalDateTime.now());
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