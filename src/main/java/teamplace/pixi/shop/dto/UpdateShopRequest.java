package teamplace.pixi.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShopRequest {
    private String shopName;
    private String shopCall;
    private String shopLoc;
}
