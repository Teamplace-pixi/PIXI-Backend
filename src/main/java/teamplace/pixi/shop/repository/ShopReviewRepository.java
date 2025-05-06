package teamplace.pixi.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teamplace.pixi.shop.domain.ShopReview;

import java.util.List;

public interface ShopReviewRepository extends JpaRepository<ShopReview, Long> {
    List<ShopReview> findReviewsByShopId(Long shopId);

}
