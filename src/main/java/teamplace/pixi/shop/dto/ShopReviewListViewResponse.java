package teamplace.pixi.shop.dto;

import lombok.Getter;
import lombok.Setter;
import teamplace.pixi.shop.domain.ShopReview;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ShopReviewListViewResponse {
    private final String userName;
    private final Long reviewId;
    private final Integer reviewStar;
    private final String reviewTitle;
    private final String reivewContent;
    private final Integer reviewMoney;
    private final String reviewTime;
    private final LocalDateTime reviewDate;
    private List<String> category;

    public ShopReviewListViewResponse(ShopReview review, List<String> category) {
        this.userName = review.getUser().getNickname();
        this.reviewId = review.getReviewId();
        this.reviewStar = review.getReviewStar();
        this.reviewTitle = review.getReviewTitle();
        this.reivewContent = review.getReviewContent();
        this.reviewMoney = review.getReviewMoney();
        this.reviewTime = review.getReviewTime();
        this.reviewDate = review.getCreatedAt();
        this.category = category;
    }
}
