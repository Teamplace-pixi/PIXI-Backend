package teamplace.pixi.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.domain.ShopReview;
import teamplace.pixi.shop.dto.ShopListRequest;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByShopDevice(Integer shopDevice);
    Optional<Shop> findByShopId(Long shopId);

}

