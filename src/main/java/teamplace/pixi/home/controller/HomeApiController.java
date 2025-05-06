package teamplace.pixi.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;
import teamplace.pixi.shop.dto.ShopListRequest;
import teamplace.pixi.shop.service.ShopService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeApiController {
    private final BoardService boardService;
    private final ShopService shopService;

    @GetMapping("/home")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping("/home/board")
    public ResponseEntity<List<BoardListViewResponse>> getHomeBoardList() {
        List<BoardListViewResponse> latestBoards = boardService.getLatestBoards();
        return ResponseEntity.ok(latestBoards);
    }

    @GetMapping("home/shop")
    public ResponseEntity<List<ShopListRequest>> getHomeShopList() {
        List<ShopListRequest> latestShops = shopService.getShopListAtHome();
        return ResponseEntity.ok(latestShops);
    }
}
