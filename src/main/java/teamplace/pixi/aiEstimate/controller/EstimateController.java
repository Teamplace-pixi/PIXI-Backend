package teamplace.pixi.aiEstimate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.aiEstimate.dto.EstimateRequest;
import teamplace.pixi.aiEstimate.dto.EstimateResponseDto;
import teamplace.pixi.aiEstimate.service.EstimateService;

import java.util.List;

@RestController
@RequestMapping("/ai/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/generate") // 견적 요청
    public ResponseEntity<EstimateResponseDto> generateEstimate(@RequestBody EstimateRequest request) {
        EstimateResponseDto response = estimateService.getEstimate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save") // 견적 저장
    public ResponseEntity<Void> saveEstimate(@RequestParam String loginId,
                                             @RequestBody EstimateRequest request) {
        EstimateResponseDto response = estimateService.getEstimate(request);
        estimateService.saveEstimate(loginId, request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{loginId}") // 사용자별 저장된 견적 내역 조회
    public ResponseEntity<List<EstimateResponseDto>> getEstimateHistory(@PathVariable String loginId) {
        return ResponseEntity.ok(estimateService.getHistory(loginId));
    }
}
