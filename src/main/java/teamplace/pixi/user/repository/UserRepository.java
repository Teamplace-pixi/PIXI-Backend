package teamplace.pixi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
