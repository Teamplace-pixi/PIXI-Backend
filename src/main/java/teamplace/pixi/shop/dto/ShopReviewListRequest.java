package teamplace.pixi.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShopReviewListRequest {
    private Long reviewId;
    private Integer reviewStar;
    private String reviewTitle;
    private String reivewContent;
    private Integer reviewMoney;
    private String reviewTime;
    private LocalDateTime reviewDate;
}
