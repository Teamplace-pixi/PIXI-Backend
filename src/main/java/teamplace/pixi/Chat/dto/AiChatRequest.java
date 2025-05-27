package teamplace.pixi.Chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiChatRequest {
    private long userId;
    private String message;
}
