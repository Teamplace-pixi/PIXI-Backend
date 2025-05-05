package teamplace.pixi.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long shopId;

    private Long userId;

    private Integer reviewStar;

    private String reviewTitle;

    @Column(length = 1000)
    private String reviewContent;

    private Integer reviewMoney;

    private String reviewTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "review_created_at")
    private LocalDateTime createdAt;

}
