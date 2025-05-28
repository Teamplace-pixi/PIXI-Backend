package teamplace.pixi.matchChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.dto.MatchRoomListViewResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchRoomFacadeService {

    private final MatchRoomService matchRoomService;
    private final MatchChatService matchChatService;

    public List<MatchRoomListViewResponse> getRoomListForUser(Long currentUserId) {
        List<MatchRoom> rooms = matchRoomService.findRoomsByUserId(currentUserId);

        return rooms.stream().map(room -> {
            MatchChat lastMessage = matchChatService.findLastMessageByRoom(room);

            return MatchRoomListViewResponse.builder()
                    .roomId(room.getMroomId())
                    .userName(room.getShop().getShopName())
                    .userImg(room.getShop().getThumb())
                    .lastMsg(lastMessage != null ? lastMessage.getContent() : "")
                    .lastMsgTime(lastMessage != null ? lastMessage.getSendTime() : null)
                    .build();
        }).collect(Collectors.toList());
    }

    public List<MatchRoomListViewResponse> getRoomListForShop(Long shopId) {
        List<MatchRoom> rooms = matchRoomService.findRoomsByShopId(shopId);

        return rooms.stream().map(room -> {
            MatchChat lastMessage = matchChatService.findLastMessageByRoom(room);

            return MatchRoomListViewResponse.builder()
                    .roomId(room.getMroomId())
                    .userName(room.getUser().getNickname()) // 무조건 상대는 user
                    .userImg(null)
                    .lastMsg(lastMessage != null ? lastMessage.getContent() : "")
                    .lastMsgTime(lastMessage != null ? lastMessage.getSendTime() : null)
                    .build();
        }).collect(Collectors.toList());
    }



}
