package teamplace.pixi.Chat.dto;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageDto {
    private String content;
    private boolean isUser;
    private LocalDateTime timestamp;
}
