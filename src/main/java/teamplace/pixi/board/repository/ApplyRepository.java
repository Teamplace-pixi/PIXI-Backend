package teamplace.pixi.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.board.domain.Apply;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.shop.domain.Shop;

import java.util.Optional;


public interface ApplyRepository  extends JpaRepository<Apply, Long> {
    Optional<Apply> findByBoardAndShop(Board board, Shop shop);
}
