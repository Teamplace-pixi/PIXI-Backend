package teamplace.pixi.matchChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.repository.MatchChatRepository;
import java.util.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchChatService {
    private final MatchChatRepository matchChatRepository;

    public MatchChat save(MatchChatRequest matchChatRequest) {
        MatchChat matchChat = new MatchChat(
                matchChatRequest.getSenderId(),
                matchChatRequest.getReceiverId(),
                matchChatRequest.getMessage(),
                LocalDateTime.now(),
                false
        );
        return matchChatRepository.save(matchChat);
    }

    public List<MatchChat> getMatchChatList(Long rcv_id) {
        return matchChatRepository.findByReceiveId(rcv_id);
    }
}
