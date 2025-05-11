package teamplace.pixi.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.AddBoardRequest;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.dto.BoardViewResponse;
import teamplace.pixi.board.repository.BoardRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final DeviceRepository deviceRepository;
    private final UserService userService;



    public Board save(AddBoardRequest request) {
        User user = userService.getCurrentUser();
        Device device = findDeviceByName(request.getDeviceName());
        Board board = createBoard(request, user, device);
        return boardRepository.save(board);
    }

    public BoardViewResponse getBoardView(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구해요 글입니다."));

        User currentUser = userService.getCurrentUser(); // 로그인 사용자 정보 가져오기
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

    private Device findDeviceByName(String deviceName) {
        return deviceRepository.findByExactDeviceNameWithoutSpacesIgnoreCase(deviceName).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 기기를 찾을 수 없습니다."));
    }

    public List<BoardListViewResponse> getMyBoards(User user) {
        return boardRepository.findAllByUser(user).stream()
                .map(BoardListViewResponse::new)
                .toList();
    }

    private Board createBoard(AddBoardRequest request, User user, Device device) {
        LocalDate boardDate = LocalDate.parse(request.getBoardDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return Board.builder()
                .user(user)
                .device(device)
                .boardTitle(request.getBoardTitle())
                .boardContent(request.getBoardContent())
                .boardLoc(request.getBoardLoc())
                .boardCost(request.getBoardCost())
                .boardDate(boardDate)
                .boardStatus("모집중")
                .build();
    }
}