package teamplace.pixi.aiEstimate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.aiEstimate.dto.EstimateRequest;
import teamplace.pixi.aiEstimate.dto.EstimateResponse;
import teamplace.pixi.aiEstimate.service.EstimateService;

import java.util.List;

@RestController
@RequestMapping("/api/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/generate") // 견적 요청
    public ResponseEntity<EstimateResponse> generateEstimate(@RequestBody EstimateRequest request) {
        EstimateResponse response = estimateService.getEstimate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save") // 견적 저장
    public ResponseEntity<Void> saveEstimate(@RequestParam Long userId,
                                             @RequestBody EstimateRequest request) {
        EstimateResponse response = estimateService.getEstimate(request);
        estimateService.saveEstimate(userId, request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{userId}") // 사용자별 저장된 견적 내역 조회
    public ResponseEntity<List<EstimateResponse>> getEstimateHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(estimateService.getHistory(userId));
    }
}
