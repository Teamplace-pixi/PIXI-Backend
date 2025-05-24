package teamplace.pixi.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopListViewResponse {
    private Long shopId;
    private String shopName;
    private String shopLoc;
    private String shopOpenTime;
    private Integer shopDevice;
    private String thumb;
}
