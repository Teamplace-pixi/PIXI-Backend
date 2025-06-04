package teamplace.pixi.aiEstimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.aiEstimate.domain.Estimate;
import teamplace.pixi.user.domain.User;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    List<Estimate> findByUserId(User user); // 조회용
}