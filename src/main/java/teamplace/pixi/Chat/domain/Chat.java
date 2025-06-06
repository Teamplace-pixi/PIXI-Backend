package teamplace.pixi.Chat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import teamplace.pixi.Chat.dto.ChatMessageDto;

import java.time.LocalDateTime;
import java.util.List;

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

    @CreationTimestamp
    @Column(name = "timestamp",nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
