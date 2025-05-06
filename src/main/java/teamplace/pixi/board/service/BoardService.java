package teamplace.pixi.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.AddBoardRequest;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.dto.BoardViewResponse;
import teamplace.pixi.board.repository.BoardRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String STATUS_RECRUITING = "모집중";
    private static final String ERROR_USER_NOT_FOUND = "로그인된 사용자를 찾을 수 없습니다.";
    private static final String ERROR_DEVICE_NOT_FOUND = "해당 이름의 기기를 찾을 수 없습니다.";
    private static final String ERROR_BOARD_NOT_FOUND = "존재하지 않는 구해요 글입니다.";

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public Board save(AddBoardRequest request) {
        User user = getCurrentUser();
        Device device = findDeviceByName(request.getDeviceName());
        Board board = createBoard(request, user, device);
        return boardRepository.save(board);
    }

    public BoardViewResponse getBoardView(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구해요 글입니다."));

        User currentUser = getCurrentUser(); // 로그인 사용자 정보 가져오기
        return new BoardViewResponse(board, currentUser);
    }

    public List<BoardListViewResponse> getBoardsByDeviceId(Long deviceId) {
        return boardRepository.findByDevice_DeviceId(deviceId).stream()
                .map(BoardListViewResponse::new)
                .toList();
    }

    public List<BoardListViewResponse> getLatestBoards() {
        return boardRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(BoardListViewResponse::new)
                .toList();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_USER_NOT_FOUND));
    }

    private Device findDeviceByName(String deviceName) {
        return deviceRepository.findByExactDeviceNameWithoutSpacesIgnoreCase(deviceName).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_DEVICE_NOT_FOUND));
    }

    private Board createBoard(AddBoardRequest request, User user, Device device) {
        LocalDate boardDate = LocalDate.parse(request.getBoardDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));

        return Board.builder()
                .user(user)
                .device(device)
                .boardTitle(request.getBoardTitle())
                .boardContent(request.getBoardContent())
                .boardLoc(request.getBoardLoc())
                .boardCost(request.getBoardCost())
                .boardDate(boardDate)
                .boardStatus(STATUS_RECRUITING)
                .build();
    }
}