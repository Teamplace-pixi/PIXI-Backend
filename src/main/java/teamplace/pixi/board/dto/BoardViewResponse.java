package teamplace.pixi.board.dto;

import lombok.Getter;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.util.s3.Image;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardViewResponse {
    private final String boardTitle;
    private final String boardContent;
    private final String boardLoc;
    private final int boardCost;
    private final String boardDate;
    private final String createdAt; // "yyyy-MM-dd HH:mm"
    private final String boardStatus;
    private final String deviceName;
    private final String nickname;
    private final int rollId;
    private final List<String> imageUrls;

    public BoardViewResponse(Board board, User currentUser) {
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardLoc = board.getBoardLoc();
        this.boardCost = board.getBoardCost();
        this.boardDate = board.getBoardDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.createdAt = board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.boardStatus = board.getBoardStatus();
        this.deviceName = board.getDevice().getDeviceName();
        this.nickname = board.getUser().getNickname();
        this.rollId = currentUser.getRollId();
        this.imageUrls = board.getImages().stream()
                .map(Image::getFileUrl)
                .collect(Collectors.toList());
    }
}