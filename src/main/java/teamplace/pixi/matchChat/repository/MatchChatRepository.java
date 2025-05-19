package teamplace.pixi.matchChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.matchChat.domain.MatchChat;

import java.util.List;

public interface MatchChatRepository extends JpaRepository<MatchChat, Long> {
    List<MatchChat> findByReceiveId(Long ReceiveId);
}
