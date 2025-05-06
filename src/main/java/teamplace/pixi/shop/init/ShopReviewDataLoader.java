/*
package teamplace.pixi.shop.init;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamplace.pixi.shop.domain.ShopReview;
import teamplace.pixi.shop.repository.ShopReviewRepository;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ShopReviewDataLoader {
    private final ShopReviewRepository  shopReviewRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public CommandLineRunner loadShopReviewData() {
        return args -> {

            InputStream inputStream = getClass().getResourceAsStream("/shop_review.json");
            try{
                List<ShopReviewJson> reviews = objectMapper.readValue(inputStream, new TypeReference<List<ShopReviewJson>>(){});
                for(ShopReviewJson r : reviews){
                    ShopReview review = ShopReview.builder()
                            .reviewId(r.getReview_id())
                            .shopId(r.getShop_id())
                            .userId(r.getUser_id())
                            .reviewStar(r.getReview_star())
                            .reviewTitle(r.getTitle())
                            .reviewContent(r.getReview_content())
                            .reviewMoney(r.getReview_money())
                            .reviewTime(r.getReview_time())
                            .createdAt(r.getCreated_at())
                            .build();
                    shopReviewRepository.save(review);
                }

            }catch (Exception e) {
                System.out.println("❌ 데이터 삽입 중 오류: " + e.getMessage());
            }
        };
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ShopReviewJson{
        private  Long review_id;
        private  Long shop_id;
        private  Long user_id;
        private String title;
        private  String review_content;
        private Integer review_star;
        private Integer review_money;
        private String review_time;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime created_at;
    }

}

*/
