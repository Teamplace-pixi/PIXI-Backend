package teamplace.pixi.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddShopRequest {
    private String shopName;
    private String shopLoc;
    private String shopOpenTime;
    private String shopCall;
    private String shopDetail;
    private Integer shopDevice;
    private String shopCertification;
    private String thumb;
}
