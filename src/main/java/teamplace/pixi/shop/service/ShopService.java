package teamplace.pixi.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.domain.ShopReview;
import teamplace.pixi.shop.dto.ShopListRequest;
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

    //수리업체 리스트
    public List<ShopListRequest> getShopList(Integer type){
        List<Shop> shops = shopRepository.findByShopDevice(type);
        return shops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private ShopListRequest convertToDto(Shop shop) {
        ShopListRequest dto = new ShopListRequest();
        dto.setShopId(shop.getShopId());
        dto.setShopName(shop.getShopName());
        dto.setShopLoc(shop.getShopLoc());
        dto.setShopOpenTime(shop.getShopOpenTime());
        dto.setShopDevice(shop.getShopDevice());
        dto.setThumb(shop.getThumb());
        return dto;
    }

    //수리업체 홈 리스트
    public List<ShopListRequest> getShopListAtHome(){
        List<Shop> shops = shopRepository.getShopAtHome();
        return shops.stream()
                .map(this::convertToDto)
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
