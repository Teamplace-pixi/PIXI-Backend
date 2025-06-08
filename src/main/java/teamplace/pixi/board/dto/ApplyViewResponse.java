package teamplace.pixi.board.dto;

import lombok.Getter;
import teamplace.pixi.board.domain.Apply;
import teamplace.pixi.shop.domain.Shop;

import java.time.LocalDateTime;

@Getter
public class ApplyViewResponse {
    private Long applyId;
    private Long shopId;
    private String shopName;
    private String shopImg;
    private String shopAddr;
    private Integer applyCost;
    private LocalDateTime applyDate;
    private LocalDateTime applyAt;
    private String applyContent;
    private Long boardId;

    public ApplyViewResponse(Apply apply, Shop shop) {
        this.applyId = apply.getApplyId();
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.shopImg = shop.getThumb();
        this.shopAddr = shop.getShopLoc();
        this.applyCost = apply.getApplyCost();
        this.applyDate = apply.getApplyDate();
        this.applyAt = apply.getApplyAt();
        this.applyContent = apply.getApplyContent();
        this.boardId = apply.getBoard().getBoardId();
    }

}
