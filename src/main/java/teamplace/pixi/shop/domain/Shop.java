package teamplace.pixi.shop.domain;

import jakarta.persistence.*;
import lombok.*;
import teamplace.pixi.user.domain.User;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "shop_name", nullable = false, length = 255)
    private String shopName;

    @Column(name = "shop_loc", length = 255)
    private String shopLoc;

    @Column(name = "shop_openTime", length = 255)
    private String shopOpenTime;

    @Column(name = "shop_call", length = 255)
    private String shopCall;

    @Column(name = "shop_detail", columnDefinition = "TEXT")
    private String shopDetail;

    @Column(name = "shop_certification", length = 255)
    private String shopCertification;

    @Column(name = "shop_device")
    private Integer shopDevice;

    @Column(name = "thumb", columnDefinition = "TEXT")
    private String thumb;

    // 상호명 변경
    public Shop updateShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    // 번호 변경
    public Shop updateShopCall(String shopCall) {
        this.shopCall = shopCall;
        return this;
    }

    // 소재지 변경
    public Shop updateShopLoc(String shopLoc) {
        this.shopLoc = shopLoc;
        return this;
    }

    // 썸네일 변경
    public Shop updateThumb(String thumb) {
        this.thumb = thumb;
        return this;
    }
}
