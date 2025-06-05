package teamplace.pixi.aiEstimate.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartEstimate {
    private String partName;
    private int price;
}
