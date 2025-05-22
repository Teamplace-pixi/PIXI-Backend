package teamplace.pixi.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="device_id")
    private Device device;

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
