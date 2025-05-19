package teamplace.pixi.matchChat.messagingrabbitmq;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import teamplace.pixi.matchChat.dto.MatchChatRequest;

@Component
@RequiredArgsConstructor
public class ChatMessageReceiver {
    private final SimpMessagingTemplate messagingTemplate;

    public void receiveMessage(MatchChatRequest message) {
        System.out.println("받은 메시지: " + message);
        messagingTemplate.convertAndSend("/topic/chat", message);
        // 예: WebSocket 통해 클라이언트에게 전송

    }
}
