package teamplace.pixi.matchChat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchChatDetailResponse {
    private String rcvName;
    private Long rcvId;
    private int rollId;
    private Long shopId;
    List<MatchChatHistoryReponse> chathistory;
}
