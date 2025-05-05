package teamplace.pixi.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.Device.dto.PartListViewResponse;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.AddBoardRequest;
import teamplace.pixi.board.dto.BoardViewResponse;
import teamplace.pixi.board.service.BoardService;
import teamplace.pixi.util.error.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardApiController {
    private final BoardService boardService;

    @Operation(summary = "구해요 작성", description = "새로운 구해요 글을 작성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구해요 작성 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
    })
    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody AddBoardRequest request) {
        Board savedBoard = boardService.save(request);
        return ResponseEntity.ok(savedBoard);
    }

    @Operation(summary = "구해요 본문 조회", description = "구해요 본문 글을 조회합니다")
    @GetMapping("/board_id={boardId}")
    public BoardViewResponse getBoardView(@PathVariable Long boardId) {
        return boardService.getBoardView(boardId);
    }
}
