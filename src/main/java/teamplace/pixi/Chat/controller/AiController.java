package teamplace.pixi.Chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.Chat.dto.AiChatRequest;
import teamplace.pixi.Chat.dto.AiChatResponse;
import teamplace.pixi.Chat.service.AiService;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping
    public ResponseEntity<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        try {
            AiChatResponse response = aiService.sendToFastApi(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AiChatResponse("AI 응답 처리 실패", null));
        }
    }
}
