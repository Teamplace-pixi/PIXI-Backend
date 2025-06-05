package teamplace.pixi.matchChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.repository.MatchRoomRepository;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.service.ShopService;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchRoomService {

    private final MatchRoomRepository matchRoomRepository;
    private final UserService userService;
    private final ShopService shopService;

    public Long createOrFindMatchRoom(Long userId, Long shopId) {

        Optional<MatchRoom> existingRoom = matchRoomRepository
                .findByUser_UserIdAndShop_ShopId(userId, shopId);

        if (existingRoom.isPresent()) {
            return existingRoom.get().getMroomId();
        }

        User user = userService.getUser(userId);
        Shop shop = shopService.getShop(shopId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 shopId입니다."));


        MatchRoom newRoom = MatchRoom.builder()
                .user(user)
                .shop(shop)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        MatchRoom savedRoom = matchRoomRepository.save(newRoom);
        return savedRoom.getMroomId();
    }

    public List<MatchRoom> findRoomsByUserId(Long userId) {
        return matchRoomRepository.findByUserIdFetchShopOrderByUpdatedAtDesc(userId);
    }

    public List<MatchRoom> findRoomsForShop(Long userId) {
        Long shopId = shopService.findByownerId(userId).getShopId();
        return matchRoomRepository.findByShopIdFetchUserOrderByUpdatedAtDesc(shopId);
    }

    public MatchRoom findRoomByShopAndUser(Long shopId, Long userId) {
        return matchRoomRepository.findByUser_UserIdAndShop_ShopId(userId, shopId)
                 .orElseThrow(() -> new IllegalArgumentException("참여중인 방이 없습니다."));
    }







}
