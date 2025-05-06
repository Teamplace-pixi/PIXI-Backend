package teamplace.pixi.Chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.Chat.dto.AiChatRequest;
import teamplace.pixi.Chat.dto.AiChatResponse;
import teamplace.pixi.Chat.service.AiService;

//RestApi의 엔드포인트
@RestController
// /api/chat 으로 Post할 수 있게 경로 설정
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    //Post 요청만 받음.
    @PostMapping
    public ResponseEntity<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        // 클라이언트가 보낸 JSON 데이터를 Java 객체로 변환해서 받음.
        try {
            AiChatResponse response = aiService.sendToFastApi(request);
            //받은 데이터를 서비스로 전달
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AiChatResponse("AI 응답 처리 실패", null));
        }
    }
}
