package teamplace.pixi.matchChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.dto.MatchRoomListViewResponse;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchRoomFacadeService {

    private final MatchRoomService matchRoomService;
    private final MatchChatService matchChatService;
    private final UserService userService;

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
                    .msgType(lastMessage != null? lastMessage.getType(): null)
                    .isRead((lastMessage != null) ?matchChatService.checkIsRead(currentUserId, lastMessage) : null)
                    .build();
        }).collect(Collectors.toList());
    }

    public List<MatchRoomListViewResponse> getRoomListForShop(Long userId) {
        //shop이 chat 조회

        List<MatchRoom> rooms = matchRoomService.findRoomsForShop(userId);
        return rooms.stream().map(room -> {
            MatchChat lastMessage = matchChatService.findLastMessageByRoom(room);

            return MatchRoomListViewResponse.builder()
                    .roomId(room.getMroomId())
                    .userName(room.getUser().getNickname()) // 무조건 상대는 user
                    .userImg(null)
                    .lastMsg(lastMessage != null ? lastMessage.getContent() : "")
                    .lastMsgTime(lastMessage != null ? lastMessage.getSendTime() : null)
                    .msgType(lastMessage != null? lastMessage.getType(): null)
                    .isRead((lastMessage != null) ?matchChatService.checkIsRead(userId, lastMessage) : null)
                    .build();
        }).collect(Collectors.toList());
    }


    public boolean checkAlert(){
        User user = userService.getCurrentUser();
        Long userId = user.getUserId();
        int roll = user.getRollId();
        List<MatchRoom> rooms;
        if(roll == 1){
            rooms = matchRoomService.findRoomsForShop(userId);
        }else{
            rooms = matchRoomService.findRoomsByUserId(userId);
        }
        // Room의 마지막 메시지의 isRead 확인 -> 하나라도 있으면 true 반환

        return rooms.stream().anyMatch(room -> {
            MatchChat lastMessage = matchChatService.findLastMessageByRoom(room);
            return !matchChatService.checkIsRead(userId, lastMessage);
        });
    }

    public List<MatchRoomListViewResponse> getRoomList() {
        Long userId = userService.getCurrentUser().getUserId();
        int rollId = userService.getUserRollId(userId);
        if(rollId ==1){
            return getRoomListForShop(userId);
        }else{
            return getRoomListForUser(userId);
        }
    }





}
