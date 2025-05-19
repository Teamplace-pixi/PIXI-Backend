package teamplace.pixi.matchChat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.service.MatchChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matchChat")
public class MatchChatApiController {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    private final MatchChatService matchChatService;

//    public ChatController(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.directExchange = directExchange;
//    }

    @Operation(summary = "매칭 챗팅 전송", description = "매칭 챗팅을 전송합니다")
    @PostMapping("/send")
    public ResponseEntity<MatchChat> sendMessage(@RequestBody MatchChatRequest messageDto) {
        // 라우팅 키는 수신자 ID 등으로 설정
        String routingKey = "chat.user." + messageDto.getReceiverId();
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, messageDto);
        MatchChat mc = matchChatService.save(messageDto);
        return ResponseEntity.ok(mc);
    }

    @Operation(summary = "매칭 챗팅 기록 조회", description = "매칭 챗팅 기록 조회을 조회합니다")
    @GetMapping("/record")
    public ResponseEntity<List<MatchChat>> getChatList(@RequestParam("rcv_id") Long rcv_id) {
        List<MatchChat> mc = matchChatService.getMatchChatList(rcv_id);
        return ResponseEntity.ok(mc);
    }

}
