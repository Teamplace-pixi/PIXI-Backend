package teamplace.pixi.Chat.repository;

import teamplace.pixi.Chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByLoginIdAndSessionIdOrderByTimestampAsc(String loginId, String sessionId);
}