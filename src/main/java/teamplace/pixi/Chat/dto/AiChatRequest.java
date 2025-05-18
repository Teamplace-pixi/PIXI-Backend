package teamplace.pixi.Chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiChatRequest {
    private Long userId;
    private String message;
}
