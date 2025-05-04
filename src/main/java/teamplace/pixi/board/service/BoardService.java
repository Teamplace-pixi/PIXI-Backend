package teamplace.pixi.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.AddBoardRequest;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.repository.BoardRepository;
import teamplace.pixi.user.domain.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final DeviceRepository deviceRepository;

    public Board save(AddBoardRequest request) {
        // deviceName을 기반으로 Device 조회
        Device device = deviceRepository.findByNameLike(request.getDeviceName()).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 기기를 찾을 수 없습니다."));

        return boardRepository.save(Board.builder()
                .device(device)
                .boardTitle(request.getBoardTitle())
                .boardCost(request.getBoardCost())
                .boardDate(LocalDate.parse(request.getBoardDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .boardStatus("모집중")
                .build());
    }

    public List<BoardListViewResponse> getBoardsByDeviceId(Long deviceId) {
        List<Board> boards = boardRepository.findByDevice_DeviceId(deviceId);
        return boards.stream()
                .map(BoardListViewResponse::new)
                .toList();
    }
}

