package teamplace.pixi.matchChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamplace.pixi.matchChat.domain.MatchRoom;

import java.util.*;

public interface MatchRoomRepository extends JpaRepository<MatchRoom, Long> {
    @Query("SELECT r FROM MatchRoom r JOIN FETCH r.user WHERE r.shop.shopId = :shopId  ORDER BY r.updatedAt DESC" )
    List<MatchRoom> findByShopIdFetchUserOrderByUpdatedAtDesc(@Param("shopId") Long shopId);

    @Query("SELECT r FROM MatchRoom r JOIN FETCH r.shop WHERE r.user.userId = :userId  ORDER BY r.updatedAt DESC")
    List<MatchRoom> findByUserIdFetchShopOrderByUpdatedAtDesc(@Param("userId") Long userId);

    Optional<MatchRoom> findByUser_UserIdAndShop_ShopId(Long userId, Long shopId);


}
