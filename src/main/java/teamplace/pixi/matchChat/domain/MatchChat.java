package teamplace.pixi.matchChat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="matchChat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MatchChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mchat_id")
    private Long mchatId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mroom_id")
    private MatchRoom matchRoom;
    @Column(name = "content")
    private String content;
    @Column(name = "send_time")
    private LocalDateTime sendTime;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "type")
    private String type;

    private Long senderId;
//    @Enumerated(EnumType.STRING)
//    private ParticipantType senderType;

    private Long receiverId;
//    @Enumerated(EnumType.STRING)
//    private ParticipantType receiverType;

    public void setRead(boolean b) {
        this.isRead = b;
    }

}
