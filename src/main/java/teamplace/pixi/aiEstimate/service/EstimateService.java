package teamplace.pixi.aiEstimate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teamplace.pixi.aiEstimate.domain.Estimate;
import teamplace.pixi.aiEstimate.domain.Part;
import teamplace.pixi.aiEstimate.dto.*;
import teamplace.pixi.aiEstimate.model.*;
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

    private final String FASTAPI_URL = "http://43.201.39.205:8000/ai/estimate";

    // 1. FastAPI 호출
    public EstimateResponseDto getEstimate(EstimateRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EstimateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<EstimateResponseDto> response = restTemplate.exchange(
                FASTAPI_URL,
                HttpMethod.POST,
                entity,
                EstimateResponseDto.class
        );


        return response.getBody();
    }

    // 2. DB 저장
    public void saveEstimate(String loginId, EstimateResponseDto response) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Estimate estimate = Estimate.builder()
                .user(user)
                .estimatedCost(response.getEstimatedCost())
                .repairMethod(response.getRepairMethod())
                .caution(response.getCaution())
                .createdAt(LocalDateTime.now())
                .build();

        estimate = estimateRepository.save(estimate);

        List<Part> parts = new ArrayList<>();
        for (PartEstimateDto partEstimateDto : response.getPartEstimates()) {
            Part part = Part.builder()
                    .estimate(estimate)
                    .partName(partEstimateDto.getPartName())
                    .price(partEstimateDto.getPrice())
                    .build();
            parts.add(part);
        }

        partRepository.saveAll(parts);
        estimate.setParts(parts);
    }


    private List<PartEstimateDto> toPartEstimateDtoList(List<Part> parts){
        List<PartEstimateDto> result = new ArrayList<>();
        for (Part part : parts){
            result.add(new PartEstimateDto(part.getPartName(),part.getPrice()));
        }
        return result;
    }

    // 3. 과거 견적 내역 조회
    public List<EstimateResponseDto> getHistory(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Estimate> estimates = estimateRepository.findByUser_LoginId(user.getLoginId());
        List<EstimateResponseDto> responses = new ArrayList<>();

        for (Estimate estimate : estimates) {
            List<Part> parts = partRepository.findByEstimate(estimate);
            List<PartEstimate> partEstimates = new ArrayList<>();

            for (Part part : parts) {
                partEstimates.add(PartEstimate.builder()
                        .partName(part.getPartName())
                        .price(part.getPrice())
                        .build());
            }

            EstimateResponseDto responseDto = EstimateResponseDto.builder()
                    .estimatedCost(estimate.getEstimatedCost())
                    .repairMethod(estimate.getRepairMethod())
                    .caution(estimate.getCaution())
                    .partEstimates(toPartEstimateDtoList(parts)) // 변환 메서드 따로 분리 추천
                    .build();



            responses.add(responseDto);
        }

        return responses;
    }
}
