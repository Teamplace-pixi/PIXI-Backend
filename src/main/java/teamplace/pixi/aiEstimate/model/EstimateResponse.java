package teamplace.pixi.aiEstimate.model;

import teamplace.pixi.aiEstimate.model.PartEstimate;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class EstimateResponse {
    private String estimatedCost;
    private String repairMethod;
    private List<PartEstimate> partEstimates;
    private String caution;
}
