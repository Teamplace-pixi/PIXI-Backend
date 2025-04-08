package teamplace.pixi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long user_id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(Long user_id, String refreshToken) {
        this.user_id = user_id;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
