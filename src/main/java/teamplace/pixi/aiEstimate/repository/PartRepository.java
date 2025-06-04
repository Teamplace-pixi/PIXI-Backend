package teamplace.pixi.aiEstimate.repository;

import teamplace.pixi.aiEstimate.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.aiEstimate.domain.Estimate;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByEstimateId(Estimate estimate);
}