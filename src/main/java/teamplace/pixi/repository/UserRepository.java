package teamplace.pixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String login_id);
}
