package teamplace.pixi.aiForm.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.aiForm.dto.AiformListViewResponse;
import teamplace.pixi.aiForm.service.FormService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
public class FormApiController {
    private final FormService formService;

    @Operation(summary = "설문지 조회", description = "ai자가진단에서 설문지를 불러옵니다")
    @GetMapping("/device_type={deviceType}")
    public ResponseEntity<List<AiformListViewResponse>> getFormview(@PathVariable("deviceType") int deviceType) {
        List<AiformListViewResponse> formList = formService.getFormList(deviceType);
        return ResponseEntity.ok(formList);
    }
}
