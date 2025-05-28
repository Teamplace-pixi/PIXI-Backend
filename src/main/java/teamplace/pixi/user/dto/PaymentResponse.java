package teamplace.pixi.user.dto;

import lombok.Getter;

@Getter
public class PaymentResponse {
    private final String name;
    private final boolean isSub;

    public PaymentResponse(String name, boolean isSub){
        this.name = name;
        this.isSub = isSub;
    }
}
