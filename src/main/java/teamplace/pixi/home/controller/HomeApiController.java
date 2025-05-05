package teamplace.pixi.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeApiController {
    private final BoardService boardService;

    @Operation(summary = "홈", description = "홈 진입점")
    @GetMapping("/home")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "홈 구해요 목록", description = "홈 화면에서 최신 구해요 글 10개를 조회합니다")
    @GetMapping("/home/board")
    public ResponseEntity<List<BoardListViewResponse>> getHomeBoardList() {
        List<BoardListViewResponse> latestBoards = boardService.getLatestBoards();
        return ResponseEntity.ok(latestBoards);
    }
}
