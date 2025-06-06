package teamplace.pixi.aiEstimate.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimateResponseDto {
    private String estimatedCost;

    private String repairMethod;

    private List<PartEstimateDto> partEstimates;

    private String caution;
}
