package teamplace.pixi.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopListRequest {
    private Long shopId;
    private String shopName;
    private String shopLoc;
    private String shopOpenTime;
    private Integer shopDevice;
    private String thumb;

}
