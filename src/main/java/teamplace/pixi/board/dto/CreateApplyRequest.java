package teamplace.pixi.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateApplyRequest {
    private long boardId;
    private String applyContent;
    private Integer applyCost;
    private LocalDateTime applyDate;

}
