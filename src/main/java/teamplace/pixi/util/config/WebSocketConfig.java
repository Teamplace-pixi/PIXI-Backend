package teamplace.pixi.util.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import teamplace.pixi.util.jwt.WebSocketJwt;
import teamplace.pixi.util.websocket.JwtHandshakeInterceptor;

import java.security.Principal;
import java.util.Map;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketJwt webSocketJwt;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new JwtHandshakeInterceptor(webSocketJwt))
                .setHandshakeHandler(handshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();  // WebSocket 연결
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic");  // 메시지 구독 경로
        config.setApplicationDestinationPrefixes("/app");  // 클라이언트에서 보내는 메시지 경로
        config.setUserDestinationPrefix("/user");  // 1:1 사용자 전송 경로
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request,
                                              WebSocketHandler wsHandler,
                                              Map<String, Object> attributes) {
                String userId = (String) attributes.get("user");
                return () -> userId; // Principal.getName() → userId 반환
            }
        };
    }
}
