package teamplace.pixi.board.dto;

import lombok.Getter;
import teamplace.pixi.board.domain.Board;

import java.time.format.DateTimeFormatter;

@Getter
public class BoardListViewResponse {
    private final Long boardId;
    private final String boardTitle;
    private final int boardCost;
    private final String boardDate;
    private final String boardStatus;
    private final String deviceBrand;
    private final int deviceType;

    public BoardListViewResponse(Board board) {
        this.boardId = board.getBoardId();
        this.boardTitle = board.getBoardTitle();
        this.boardCost = board.getBoardCost();
        this.boardDate = board.getBoardDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.boardStatus = board.getBoardStatus();
        this.deviceBrand = board.getDevice().getDeviceBrand();
        this.deviceType = board.getDevice().getDeviceType();
    }
}
