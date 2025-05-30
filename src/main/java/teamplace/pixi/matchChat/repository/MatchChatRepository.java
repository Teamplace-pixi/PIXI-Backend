package teamplace.pixi.matchChat.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamplace.pixi.matchChat.domain.MatchChat;
import teamplace.pixi.matchChat.domain.MatchRoom;

import java.util.List;

public interface MatchChatRepository extends JpaRepository<MatchChat, Long> {
    //List<MatchChat> findByMatchRoom_roomId(Long roomId);
    MatchChat findTopByMatchRoomOrderBySendTimeDesc(MatchRoom room);
    List<MatchChat> findByMatchRoomOrderBySendTimeAsc(MatchRoom room);
    @Modifying
    @Transactional
    @Query("UPDATE MatchChat m SET m.isRead = true WHERE m.matchRoom.mroomId = :roomId AND m.receiverId = :receiverId")
    int markMessagesAsRead(@Param("roomId") Long roomId, @Param("receiverId") Long receiverId);


}
