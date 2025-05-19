package teamplace.pixi.matchChat.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import teamplace.pixi.matchChat.messagingrabbitmq.RabbitMqManager;
import org.springframework.web.socket.messaging.SessionConnectEvent;


@Component
@RequiredArgsConstructor
public class WebSocketEventHandler {

    private final RabbitMqManager rabbitMqManager;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        System.out.println("WebSocket SessionConnectEvent 발생!");
        // 유저 ID를 헤더에서 추출 (또는 세션 정보)
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = accessor.getFirstNativeHeader("userId");
        System.out.println("WebSocket Connected userId: " + userId);


        if (userId != null) {
            rabbitMqManager.registerUserQueue(userId);
        }
    }
}
