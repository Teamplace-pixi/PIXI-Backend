package teamplace.pixi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamplace.pixi.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    List<User> findAllBySubscriptionEndDateBefore(LocalDateTime dateTime);
    @Query("SELECT u.rollId FROM User u WHERE u.userId = :userId")
    Integer findRollIdByUserId(@Param("userId") Long userId);
}
