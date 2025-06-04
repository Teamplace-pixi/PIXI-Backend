package teamplace.pixi.matchChat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.domain.ParticipantType;
import teamplace.pixi.matchChat.dto.MatchChatDetailResponse;
import teamplace.pixi.matchChat.dto.MatchChatHistoryReponse;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.repository.MatchChatRepository;
import teamplace.pixi.matchChat.repository.MatchRoomRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.service.UserService;

import java.util.List;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchChatService {
    private final MatchChatRepository matchChatRepository;
    private final MatchRoomRepository matchRoomRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    public MatchChat save(MatchChatRequest matchChatRequest, String type) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchChatRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 room id입니다"));

        Long userId = userService.getCurrentUser().getUserId();

        MatchChat matchChat = MatchChat.builder()
                .matchRoom(matchRoom)
                .content(matchChatRequest.getMessage())
                .sendTime(LocalDateTime.now())
                .isRead(false)
                .type(type)
                .senderId(userId)
                .receiverId(matchChatRequest.getReceiverId())
                .build();

        matchRoom.UpdateTime(LocalDateTime.now());
        return matchChatRepository.save(matchChat);
    }

    public void sendMessage(MatchChatRequest messageDto, String type) {
        String routingKey = "chat.user." + messageDto.getReceiverId();
        rabbitTemplate.convertAndSend("chat.direct", routingKey, messageDto);
        save(messageDto, type);
    }

    public MatchChat findLastMessageByRoom(MatchRoom room) {
        return matchChatRepository.findTopByMatchRoomOrderBySendTimeDesc(room);
    }

    @Transactional
    public MatchChatDetailResponse getChatHistory(Long roomId) {
        Long userId = userService.getCurrentUser().getUserId();
        MatchRoom r = matchRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방을 찾을 수 없습니다."));
        //rollId 1: shop, 0: user
        Integer rollId = userService.getUserRollId(userId);
        String rcvName;
        Long rcvId;
        Long shopId;

        if(rollId == 1){
            //내가 shop -> rcv는 user
            rcvName = r.getUser().getNickname();
            rcvId = r.getUser().getUserId();
            shopId = null;
            matchChatRepository.markMessagesAsRead(roomId, userId); // 읽음처리
        }else{
            //내가 user -> rcv는 shop
            rcvName = r.getShop().getShopName();
            rcvId = r.getShop().getUserId();
            shopId = r.getShop().getShopId();
            matchChatRepository.markMessagesAsRead(roomId, userId); // 읽음처리
        }

        List<MatchChat> chatlist =  matchChatRepository.findByMatchRoomOrderBySendTimeAsc(r);

        List<MatchChatHistoryReponse> dtoList = chatlist.stream()
                .map(chat -> {
                    boolean viewRead = chat.getSenderId().equals(userId)? true : chat.isRead(); // 💡 핵심 처리
                    return new MatchChatHistoryReponse(chat, viewRead);
                })
                .collect(Collectors.toList());

        new MatchChatDetailResponse();
        return MatchChatDetailResponse.builder()
                .chathistory(dtoList)
                .shopId(shopId)
                .rcvId(rcvId)
                .rcvName(rcvName)
                .rollId(rollId)
                .build();

    }

    public boolean checkIsRead(Long userId, MatchChat msg){

        boolean isMine =  msg.getSenderId().equals(userId);
        //맞으면 true 반환, 상대가 보낸 메시지라면 db 접근해서 db값 반환하게
        if(isMine){
            return true;
        }else{
            return msg.isRead();
        }
    }


}
