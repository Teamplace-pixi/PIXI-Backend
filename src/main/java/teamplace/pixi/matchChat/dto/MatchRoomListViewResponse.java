package teamplace.pixi.matchChat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MatchRoomListViewResponse {
    private Long roomId;
    private String userName;
    private String userImg;
    private String lastMsg;
    private LocalDateTime lastMsgTime;

}
