package teamplace.pixi.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finder")
public class FinderController {
    private final BoardService boardService;

    @GetMapping("/boardList/device_id={deviceId}")
    public ResponseEntity<List<BoardListViewResponse>> getBoardsByDeviceId(@PathVariable Long deviceId) {
        List<BoardListViewResponse> boards = boardService.getBoardsByDeviceId(deviceId);
        return ResponseEntity.ok(boards);
    }
}
