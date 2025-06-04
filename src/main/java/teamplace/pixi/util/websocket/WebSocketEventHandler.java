package teamplace.pixi.util.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import teamplace.pixi.matchChat.messagingrabbitmq.RabbitMqManager;
import teamplace.pixi.util.jwt.WebSocketJwt;


@Component
@RequiredArgsConstructor
public class WebSocketEventHandler {

    private final RabbitMqManager rabbitMqManager;
    private final WebSocketJwt webSocketJwt;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String token = accessor.getFirstNativeHeader("Authorization"); // 클라이언트에서 보내는 헤더
        System.out.println("Received token: " + token);
        if (token != null && webSocketJwt.validateToken(token)) {
            Long userId = webSocketJwt.extractUserId(token);
            System.out.println("수신 유저 id: " + userId);
            rabbitMqManager.registerUserQueue(userId); // 사용자 큐 등록
        } else {
            System.out.println("Invalid JWT Token during WebSocket handshake");
        }
    }
}
