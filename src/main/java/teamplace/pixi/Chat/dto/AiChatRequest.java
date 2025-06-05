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
}
