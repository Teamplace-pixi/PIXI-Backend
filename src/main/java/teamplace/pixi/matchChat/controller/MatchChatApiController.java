package teamplace.pixi.matchChat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.ParticipantType;
import teamplace.pixi.matchChat.dto.MatchChatDetailResponse;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.dto.MatchRoomListViewResponse;
import teamplace.pixi.matchChat.service.MatchChatService;
import teamplace.pixi.matchChat.service.MatchRoomFacadeService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matchChat")
public class MatchChatApiController {

    private final MatchRoomFacadeService matchRoomFacadeService;
    private final MatchChatService matchChatService;

    @Operation(summary = "메시지 전송", description = "메시지를 전송합니다")
    @PostMapping("/send")
    public ResponseEntity<String> sendChat(@RequestBody MatchChatRequest chatRequest) {
        matchChatService.sendMessage(chatRequest, "msg");
        return ResponseEntity.ok("전송 완료");
    }

    @Operation(summary = "유저의 chat 목록", description = "유저의 챗팅방 목록을 조회합니다")
    @GetMapping("/rooms")
    public ResponseEntity<List<MatchRoomListViewResponse>> getRooms() {

        List<MatchRoomListViewResponse> roomList = matchRoomFacadeService.getRoomList();
        return ResponseEntity.ok(roomList);
    }

    @Operation(summary = "유저 chat 기록 조회", description = "특정 유저와 챗팅한 과거 기록을 조회합니다")
    @GetMapping("/room/{roomId}")
    public ResponseEntity<MatchChatDetailResponse> getRoom(@PathVariable("roomId") Long roomId) {
        MatchChatDetailResponse resp = matchChatService.getChatHistory(roomId);
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "알림 표시", description = "현재 안읽은 메시지가 있는지 조호합니다")
    @GetMapping("/Alert")
    public boolean checkAlert() {
        return matchRoomFacadeService.checkAlert();
    }

}
