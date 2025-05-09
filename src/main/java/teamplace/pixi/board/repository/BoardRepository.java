package teamplace.pixi.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.user.domain.User;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByDevice_DeviceId(Long deviceId);
    List<Board> findTop10ByOrderByCreatedAtDesc();
    List<Board> findAllByUser(User user);
}