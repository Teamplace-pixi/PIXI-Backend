package teamplace.pixi.matchChat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private final RabbitTemplate rabbitTemplate;
    private final MatchRoomFacadeService matchRoomFacadeService;
    private final MatchChatService matchChatService;

    @PostMapping("/send")
    public ResponseEntity<String> sendChat(@RequestBody MatchChatRequest chatRequest) {
        matchChatService.sendMessage(chatRequest);
        return ResponseEntity.ok("메시지가 전송되었습니다.");
    }

//    @GetMapping("/rooms/{userId}")
//    public ResponseEntity<List<MatchRoomListViewResponse>> getRooms(@PathVariable("userId") Long userId,
//                                                                    @RequestParam("userType") ParticipantType userType) {
//
//        List<MatchRoomListViewResponse> roomList = userType == ParticipantType.SHOP
//                ? matchRoomFacadeService.getRoomListForShop(userId)
//                : matchRoomFacadeService.getRoomListForUser(userId);
//
//        return ResponseEntity.ok(roomList);
//    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<MatchChatDetailResponse> getRoom(@PathVariable("roomId") Long roomId, @RequestParam("userId") Long userId) {
        MatchChatDetailResponse resp = matchChatService.getChatHistory(roomId, userId);
        return ResponseEntity.ok(resp);
    }

}
