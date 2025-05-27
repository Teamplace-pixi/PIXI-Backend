package teamplace.pixi.aiEstimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstimateResponse {
    private String diagnos;          // 예: 소리가 안들리고 ~
    private List<PartEstimate> partEstimates; // 예 -> 스피커 모듈 - 100,000원
    private String caution;                   // 예: 메인보드 불량, 20만원 이상 비용 발생 가능

    @Getter
    @Builder
    public static class PartEstimate { // 고장 부품 여러개일수도 있으니 함수를 만들어 관리
        private String partName; // 필요 부품 이름
        private int price; // 그 부품 가격
    }
}
