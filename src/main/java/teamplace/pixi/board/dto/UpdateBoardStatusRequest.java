package teamplace.pixi.board.dto;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class UpdateBoardStatusRequest {
    @Schema(
            description = "변경할 상태 (모집중, 예약중, 모집 완료 중 하나)",
            example = "모집중"
    )
    private String status;
    @Schema(
            description = "수리해줄 shopId"
    )
    private Long shopId;

}
