package teamplace.pixi.matchChat.messagingrabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import teamplace.pixi.matchChat.domain.ParticipantType;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.dto.MatchRoomListViewResponse;
import teamplace.pixi.matchChat.service.MatchRoomFacadeService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageReceiver {

    private final SimpMessagingTemplate messagingTemplate;

    public void receiveMessage(MatchChatRequest message) {
        System.out.println("메시지 수신: "+message.getMessage() + ", 수신자: " + message.getReceiverId()+", 송신자" + message.getSenderId());
        Long receiverId = message.getReceiverId();
        Long senderId = message.getSenderId();

        // 채팅방에 메시지 전송
        messagingTemplate.convertAndSendToUser(
                String.valueOf(receiverId),
                "/queue/messages."+ message.getRoomId(),
                message
        );


        messagingTemplate.convertAndSendToUser(
                String.valueOf(senderId),
                "/queue/messages." + message.getRoomId(),
                message
        );

        // 채팅방 목록 갱신
//        List<MatchRoomListViewResponse> roomList = (message.getReceiverType() == ParticipantType.SHOP)
//                ? matchRoomFacadeService.getRoomListForShop(receiverId)
//                : matchRoomFacadeService.getRoomListForUser(receiverId);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(receiverId),
                "/queue/alert",
                "newMsg"
        );
    }


}
