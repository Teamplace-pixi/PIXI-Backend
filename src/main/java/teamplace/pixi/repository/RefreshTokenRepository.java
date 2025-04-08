package teamplace.pixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long user_id);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
