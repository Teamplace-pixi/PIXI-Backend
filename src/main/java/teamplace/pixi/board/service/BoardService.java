package teamplace.pixi.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.dto.PartListViewResponse;
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
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public Board save(AddBoardRequest request) {
        // 현재 로그인된 사용자 정보 조회
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자를 찾을 수 없습니다."));

        // 정확히 일치하는 deviceName으로 Device 조회
        Device device = deviceRepository.findByExactDeviceNameWithoutSpacesIgnoreCase(request.getDeviceName()).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 기기를 찾을 수 없습니다."));

        return boardRepository.save(Board.builder()
                .user(user)
                .device(device)
                .boardTitle(request.getBoardTitle())
                .boardContent(request.getBoardContent())
                .boardLoc(request.getBoardLoc())
                .boardCost(request.getBoardCost())
                .boardDate(LocalDate.parse(request.getBoardDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .boardStatus("모집중")
                .build());
    }

    public BoardViewResponse getBoardView(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구해요 글입니다."));
        return new BoardViewResponse(board);
    }

    public List<BoardListViewResponse> getBoardsByDeviceId(Long deviceId) {
        List<Board> boards = boardRepository.findByDevice_DeviceId(deviceId);
        return boards.stream()
                .map(BoardListViewResponse::new)
                .toList();
    }

    public List<BoardListViewResponse> getLatestBoards() {
        List<Board> boards = boardRepository.findTop10ByOrderByCreatedAtDesc();
        return boards.stream()
                .map(BoardListViewResponse::new)
                .toList();
    }
}

