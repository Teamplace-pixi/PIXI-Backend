package teamplace.pixi.board.dto;

import lombok.Getter;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.user.domain.User;

import java.time.format.DateTimeFormatter;

@Getter
public class BoardViewResponse {
    private final String boardTitle;
    private final String boardContent;
    private final String boardLoc;
    private final int boardCost;
    private final String boardDate; // "yyyy-MM-dd"
    private final String deviceName;
    private final String nickname;
    private final int rollId;

    public BoardViewResponse(Board board, User currentUser) {
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardLoc = board.getBoardLoc();
        this.boardCost = board.getBoardCost();
        this.boardDate = board.getBoardDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.deviceName = board.getDevice().getDeviceName();
        this.nickname = board.getUser().getNickname();
        this.rollId = currentUser.getRollId();
    }
}