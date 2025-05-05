package teamplace.pixi.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.dto.ShopListRequest;
import teamplace.pixi.shop.dto.ShopReviewListRequest;
import teamplace.pixi.shop.service.ShopService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopApiController {
    private final ShopService shopService;

    @Operation(summary = "수리업체 리스트 조회", description = "수리업체 리스트를 조회합니다")
    @GetMapping("")
    public List<ShopListRequest> getShopList(@RequestParam("device_type") Integer type){
        return shopService.getShopList(type);
    }

    @Operation(summary = "수리업체 상세 조회", description = "수리업체 상세 정보를 조회합니다")
    @GetMapping("/shop_id/{id}")
    public Optional<Shop> getShop(@PathVariable("id") Long id){
        return shopService.getShop(id);
    }

    @Operation(summary = "수리업체 리뷰 조회", description = "수리업체 리뷰를 조회합니다")
    @GetMapping("/review/shop_id/{id}")
    public List<ShopReviewListRequest> getShopReveiwList(@PathVariable("id") Long shop_id){
        return shopService.getShopReviews(shop_id);
    }

}
