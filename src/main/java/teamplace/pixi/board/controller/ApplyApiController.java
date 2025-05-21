package teamplace.pixi.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.board.dto.ApplyViewResponse;
import teamplace.pixi.board.dto.CreateApplyRequest;
import teamplace.pixi.board.service.ApplyService;
import teamplace.pixi.util.error.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyApiController {
    private final ApplyService applyService;

    @Operation(summary="구해요 지원서 작성", description = "해당 구해요 글에 지원합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지원 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
    })
    @PostMapping
    public ResponseEntity<?> createApply(@RequestBody CreateApplyRequest request) {
        applyService.save(request);
        return ResponseEntity.ok("지원 성공");
    }

    @Operation(summary = "지원서 조회" , description = "지원서를 조회합니다")
    @GetMapping("/apply_id={applyId}")
    public ApplyViewResponse getApplyById(@PathVariable("applyId") Long applyId) {return applyService.getApplyView(applyId);}

    @Operation(summary = "지원 여부 조회" , description = "특정 구해요글에 대해 지원 여부를 조회합니다")
    @GetMapping("check")
    public ResponseEntity<?> checkApply(@RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) {return ResponseEntity.ok(applyService.hasapplied(boardId, userId));}




}
