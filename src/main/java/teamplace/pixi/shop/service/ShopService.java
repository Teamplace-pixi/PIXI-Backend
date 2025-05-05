package teamplace.pixi.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.domain.ShopReview;
import teamplace.pixi.shop.dto.ShopListViewResponse;
import teamplace.pixi.shop.dto.ShopReviewListRequest;
import teamplace.pixi.shop.repository.ShopRepository;
import teamplace.pixi.shop.repository.ShopReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopReviewRepository shopReviewRepository;
    private final DeviceRepository deviceRepository;

    //수리업체 리스트
    public List<ShopListViewResponse> getShopList(Integer type){
        List<Shop> shops = shopRepository.findByShopDevice(type);
        return shops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private ShopListViewResponse convertToDto(Shop shop) {
        ShopListViewResponse dto = new ShopListViewResponse();
        dto.setShopId(shop.getShopId());
        dto.setShopName(shop.getShopName());
        dto.setShopLoc(shop.getShopLoc());
        dto.setShopOpenTime(shop.getShopOpenTime());
        dto.setShopDevice(shop.getShopDevice());
        dto.setThumb(shop.getThumb());
        return dto;
    }

    // 디바이스 타입에 따른 수리업체 조회
    public List<ShopListViewResponse> getShopsListByDeviceId(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 기기입니다."));

        int deviceType = device.getDeviceType();
        List<Shop> shops;

        if (deviceType == 0 || deviceType == 2 || deviceType == 3) {
            shops = shopRepository.findByShopDevice(0);
        } else if (deviceType == 1) {
            shops = shopRepository.findByShopDevice(1);
        } else {
            throw new IllegalArgumentException("지원하는 수리업체가 없는 기기입니다.");
        }

        // Shop -> ShopListViewResponse 변환
        return shops.stream()
                .map(this::convertToDto) // 또는 new ShopListViewResponse(shop)
                .collect(Collectors.toList());
    }

    //수리업체 상세
    public Optional<Shop> getShop(Long id){
        return shopRepository.findByShopId(id);
    }

    //수리업체 후기 리스트
    public List<ShopReviewListRequest> getShopReviews(Long shopId){
        List<ShopReview> reviews = shopReviewRepository.findReviewsByShopId(shopId);
        return reviews.stream()
                .map(this::ReviewconvertToDto)
                .collect(Collectors.toList());
    }

    private ShopReviewListRequest ReviewconvertToDto(ShopReview shopReview) {
        ShopReviewListRequest dto = new ShopReviewListRequest();
        dto.setReviewId(shopReview.getReviewId());
        dto.setReviewStar(shopReview.getReviewStar());
        dto.setReviewTitle(shopReview.getReviewTitle());
        dto.setReivewContent(shopReview.getReviewContent());
        dto.setReviewMoney(shopReview.getReviewMoney());
        dto.setReviewTime(shopReview.getReviewTime());
        dto.setReviewDate(shopReview.getCreatedAt());
        return dto;
    }

}
