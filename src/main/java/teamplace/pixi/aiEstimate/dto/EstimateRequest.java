package teamplace.pixi.aiEstimate.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstimateRequest {
    private int deviceTypeCode;        // enum 으로 변환
    private String brand;              // 예: 애플, 삼성
    private String model;              // 예: 아이폰 13, 갤럭시 S21
    private String symptom;        // 사용자가 작성한 증상 설명
    private List<String> selfCheckResults; // 자가진단 결과 예: {"카메라": true, "오디오": false, ...}
}
