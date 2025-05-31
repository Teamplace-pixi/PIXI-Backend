package teamplace.pixi.matchChat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamplace.pixi.matchChat.domain.MatchChat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchChatHistoryReponse {

    private String msgType;
    private Long matchId;
    private String content;
    private LocalDateTime sendTime;
    private boolean isRead;
    private Long senderId;

    public MatchChatHistoryReponse(MatchChat chat, boolean isRead) {
        this.matchId = chat.getMchatId();
        this.content = chat.getContent();
        this.sendTime = chat.getSendTime();
        this.isRead = isRead;
        this.senderId = chat.getSenderId();
        this.msgType = chat.getType();
    }

}
