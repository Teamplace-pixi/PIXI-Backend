package teamplace.pixi.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teamplace.pixi.shop.domain.Shop;


import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByShopDevice(Integer shopDevice);
    Optional<Shop> findByShopId(Long shopId);
    Shop findByUserId(Long userId);
    @Query(value = "SELECT s.*, COUNT(r.shop_id) AS review_count " +
            "FROM shop s " +
            "LEFT JOIN review r ON s.shop_id = r.shop_id " +
            "GROUP BY s.shop_id " +
            "ORDER BY review_count DESC LIMIT 10",
            nativeQuery = true)
    List<Shop> getShopAtHome();



}