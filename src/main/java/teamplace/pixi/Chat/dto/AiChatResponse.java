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
    private String sessionId; //세션 아이디 축아

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
