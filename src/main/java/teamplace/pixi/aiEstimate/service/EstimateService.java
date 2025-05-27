package teamplace.pixi.aiEstimate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.aiEstimate.dto.EstimateRequest;
import teamplace.pixi.aiEstimate.dto.EstimateResponse;
import teamplace.pixi.aiEstimate.dto.EstimateResponse.PartEstimate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstimateService {

    public EstimateResponse generateEstimate(EstimateRequest request) {
        // AI 진단 결과 (예시)
        String diagnos = "스피커 모듈 고장 가능성이 높습니다.";

        List<PartEstimate> partEstimates = List.of(
                PartEstimate.builder()
                        .partName("스피커 모듈")
                        .price(100000)
                        .build(),
                PartEstimate.builder()
                        .partName("방수 테이프")
                        .price(15000)
                        .build()
        );

        String caution = "메인보드 손상 시 수리비가 20만원 이상 발생할 수 있습니다.";

        return EstimateResponse.builder()
                .diagnos(diagnos)
                .partEstimates(partEstimates)
                .caution(caution)
                .build();
    }
}
