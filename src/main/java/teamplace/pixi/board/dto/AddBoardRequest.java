package teamplace.pixi.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddBoardRequest {
    private String boardTitle;
    private String boardContent;
    private String boardLoc;
    private int boardCost;
    private String boardDate; // "yyyy-MM-dd"
    private String deviceName;
    private List<Long> imageIds;
}