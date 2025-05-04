package teamplace.pixi.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping("/home/board")
    public ResponseEntity<List<BoardListViewResponse>> getHomeBoardList() {
        List<BoardListViewResponse> latestBoards = boardService.getLatestBoards();
        return ResponseEntity.ok(latestBoards);
    }
}
