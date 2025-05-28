package teamplace.pixi.matchChat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamplace.pixi.matchChat.domain.ParticipantType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchChatRequest {
    private Long roomId;
    private String message;
    private String type; // "info"와 "msg"로 구분
    private Long senderId;
    private ParticipantType senderType;
    private Long receiverId;
    private ParticipantType receiverType;
//    private LocalDateTime sendTime;
}
