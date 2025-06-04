package teamplace.pixi.util.websocket;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import teamplace.pixi.util.jwt.WebSocketJwt;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final WebSocketJwt webSocketJwt;

    public JwtHandshakeInterceptor(WebSocketJwt webSocketJwt) {
        this.webSocketJwt = webSocketJwt;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        String token = null;

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

            token = httpServletRequest.getParameter("token");

            if (token != null && webSocketJwt.validateToken(token)) {
                Long userId = webSocketJwt.extractUserId(token);
                System.out.println("🟢 WebSocket Handshake - 유효한 토큰, userId: " + userId);
                attributes.put("user", String.valueOf(userId));
                return true;
            } else {
                System.out.println("🔴 WebSocket Handshake - 유효하지 않은 토큰");
                return false;
            }
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // 필요시 후처리 가능
    }
}
