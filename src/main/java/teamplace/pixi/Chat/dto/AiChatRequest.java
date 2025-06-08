package teamplace.pixi.Chat.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class AiChatRequest {

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("message")
    private String message;

    //세션 아이디 추가
    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("newChat")
    private boolean newChat;
}
