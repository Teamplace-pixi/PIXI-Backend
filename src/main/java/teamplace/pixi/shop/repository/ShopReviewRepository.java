package teamplace.pixi.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.shop.domain.ShopReview;

import java.util.List;

public interface ShopReviewRepository extends JpaRepository<ShopReview, Long> {
    List<ShopReview> findReviewsByShopId(Long shopId);
}
