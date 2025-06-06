package teamplace.pixi.Chat.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse {
    private String reply;
    private boolean recommend; // "goohyeo" true "평소" false
}
