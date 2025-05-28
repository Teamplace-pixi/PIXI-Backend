package teamplace.pixi.matchChat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.dto.MatchChatDetailResponse;
import teamplace.pixi.matchChat.dto.MatchChatHistoryReponse;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.repository.MatchChatRepository;
import teamplace.pixi.matchChat.repository.MatchRoomRepository;
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
    private final MatchRoomService matchRoomService;

    public MatchChat save(MatchChatRequest matchChatRequest) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchChatRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 room id입니다"));

        MatchChat matchChat = MatchChat.builder()
                .matchRoom(matchRoom)
                .content(matchChatRequest.getMessage())
                .sendTime(LocalDateTime.now())
                .isRead(false)
                .type(matchChatRequest.getType())
                .senderId(matchChatRequest.getSenderId())
                .senderType(matchChatRequest.getSenderType())
                .receiverId(matchChatRequest.getReceiverId())
                .receiverType(matchChatRequest.getReceiverType())
                .build();

        return matchChatRepository.save(matchChat);
    }

    public void sendMessage(MatchChatRequest messageDto) {
        String routingKey = "chat.user." + messageDto.getReceiverId();
        rabbitTemplate.convertAndSend("chat.direct", routingKey, messageDto);
        save(messageDto);
    }

    public MatchChat findLastMessageByRoom(MatchRoom room) {
        return matchChatRepository.findTopByMatchRoomOrderBySendTimeDesc(room);
    }

    @Transactional
    public MatchChatDetailResponse getChatHistory(Long roomId, Long userId) {
        MatchRoom r = matchRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방을 찾을 수 없습니다."));
        List<MatchChat> chatlist =  matchChatRepository.findByMatchRoomOrderBySendTimeAsc(r);
        for (MatchChat chat : chatlist) {
            // 내가 보낸건 읽음처리
            if (chat.getSenderId().equals(userId)) {
                chat.setRead(true);
            }
        }
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
            matchChatRepository.markMessagesAsRead(roomId, userId);
        }else{
            //내가 user -> rcv는 shop
            rcvName = r.getUser().getNickname();
            rcvId = r.getUser().getUserId();
            shopId = r.getShop().getShopId();
            matchChatRepository.markMessagesAsRead(roomId, userId);
        }

        List<MatchChatHistoryReponse> dtoList = chatlist.stream()
                .map(MatchChatHistoryReponse::new) // 필요한 필드만 뽑아서 담는 생성자
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



}
