package teamplace.pixi.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse {
    private String reply;
    private String recommend; // "goohyeo" 또는 null
}
