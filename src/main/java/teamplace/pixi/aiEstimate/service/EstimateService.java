package teamplace.pixi.aiEstimate.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teamplace.pixi.aiEstimate.domain.Estimate;
import teamplace.pixi.aiEstimate.domain.Part;
import teamplace.pixi.aiEstimate.dto.EstimateRequest;
import teamplace.pixi.aiEstimate.dto.EstimateResponse;
import teamplace.pixi.aiEstimate.dto.EstimateResponse.PartEstimate;
import teamplace.pixi.aiEstimate.repository.EstimateRepository;
import teamplace.pixi.aiEstimate.repository.PartRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FASTAPI_URL = "http://13.124.186.0:8000/ai/estimate";

    // 1. FastAPI 호출
    public EstimateResponse getEstimate(EstimateRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EstimateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<EstimateResponse> response = restTemplate.exchange(
                FASTAPI_URL,
                HttpMethod.POST,
                entity,
                EstimateResponse.class
        );

        return response.getBody();
    }

    // 2. DB 저장
    public void saveEstimate(Long userId, EstimateRequest request, EstimateResponse response) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Estimate estimate = Estimate.builder()
                .user(user)
                .estimatedCost(response.getEstimatedCost())
                .repairMethod(response.getRepairMethod())
                .caution(response.getCaution())
                .createdAt(LocalDateTime.now())
                .build();

        // Save estimate first to get the ID
        estimate = estimateRepository.save(estimate);

        // Save parts
        List<Part> parts = new ArrayList<>();
        for (PartEstimate pe : response.getPartEstimates()) {
            Part part = Part.builder()
                    .estimate(estimate)
                    .partName(pe.getPartName())
                    .price(pe.getPrice())
                    .build();
            parts.add(part);
        }

        partRepository.saveAll(parts);
        estimate.setParts(parts); // 양방향 매핑을 위한 setter
    }

    // 3. 과거 견적 내역 조회
    public List<EstimateResponse> getHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Estimate> estimates = estimateRepository.findByUserId(user);
        List<EstimateResponse> responses = new ArrayList<>();

        for (Estimate estimate : estimates) {
            List<Part> parts = partRepository.findByEstimateId(estimate);
            List<PartEstimate> partEstimates = new ArrayList<>();

            for (Part part : parts) {
                partEstimates.add(PartEstimate.builder()
                        .partName(part.getPartName())
                        .price(part.getPrice())
                        .build());
            }

            EstimateResponse response = EstimateResponse.builder()
                    .estimatedCost(estimate.getEstimatedCost())
                    .repairMethod(estimate.getRepairMethod())
                    .caution(estimate.getCaution())
                    .partEstimates(partEstimates)
                    .build();

            responses.add(response);
        }

        return responses;
    }
}
