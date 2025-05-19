package teamplace.pixi.matchChat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="matchChat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MatchChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mchat_id")
    private Long mchatId;
    @Column(name = "send_id")
    private Long sendId;
    @Column(name = "receive_id")
    private Long receiveId;
    @Column(name = "content")
    private String content;
    @Column(name = "send_time")
    private LocalDateTime sendTime;
    @Column(name = "is_read")
    private boolean isRead;

    public MatchChat(Long sendId, Long receiveId, String content, LocalDateTime sendTime, boolean is_read) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.content = content;
        this.sendTime = sendTime;
        this.isRead = is_read;
    }

}
