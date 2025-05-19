package teamplace.pixi.matchChat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchChatRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
}
