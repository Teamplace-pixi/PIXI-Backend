package teamplace.pixi.shop.dto;

import lombok.Getter;

@Getter
public class AddReviewRequest {
    private Long user_id;
    private Long shop_id;
    private Long device_id;
    private Integer reviewStar;
    private String reviewTitle;
    private String reviewContent;
    private String reviewTime;
    private Integer reviewMoney;

}
