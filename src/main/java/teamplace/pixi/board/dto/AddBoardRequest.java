package teamplace.pixi.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddBoardRequest {
    private String boardTitle;
    private int boardCost;
    private String boardDate; // "yyyy-MM-dd HH:mm"
    private String deviceName;
}