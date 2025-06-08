package teamplace.pixi.Chat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="Chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "user_id",updatable = false)
    private Long userId;

    @Column(name = "login_id",updatable = false)
    private String loginId;

    @Column(name = "is_user",nullable = false)
    private Boolean isUser; // true 면 사용자, false 면 AI

    @Column(name = "content",nullable = false)
    private String content;

    // 세션 아이디 -> 채팅방 구분용
    @Column(name = "sessionId", nullable = false)
    private String sessionId;

    // 채팅방 제목용
    @Column(name = "newChat")
    private boolean newChat;

    @CreationTimestamp
    @Column(name = "timestamp",nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
