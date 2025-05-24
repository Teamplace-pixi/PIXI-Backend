package teamplace.pixi.board.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.AddBoardRequest;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.dto.BoardViewResponse;
import teamplace.pixi.board.repository.BoardRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.service.UserService;
import teamplace.pixi.util.s3.AwsS3Service;
import teamplace.pixi.util.s3.Image;
import teamplace.pixi.util.s3.ImageRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final AwsS3Service awsS3Service;
    private final ImageRepository imageRepository;

    @Transactional
    public Board save(AddBoardRequest request, List<MultipartFile> multipartFiles) {
        User user = userService.getCurrentUser();
        Device device = findDeviceByName(request.getDeviceName());
        List<Image> uploadedImages = awsS3Service.uploadMultiFile(multipartFiles);

        Board board = createBoard(request, user, device);
        for (Image image : uploadedImages) {
            image.setBoard(board);
        }

        return boardRepository.save(board);
    }

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        List<String> fileNames = board.getImages().stream()
                .map(Image::getFileName)
                .toList();

        awsS3Service.deleteFile(fileNames);
        imageRepository.deleteAll(board.getImages());
        boardRepository.delete(board);
    }

    @Transactional
    public void updateBoardStatus(Long boardId, String status){
        Board b = boardRepository.findByBoardId(boardId);
        if (b == null) {
            throw new EntityNotFoundException("해당 boardId에 해당하는 게시판이 존재하지 않습니다: " + boardId);
        }
        b.updateBoardStatus(status);
    }



    public BoardViewResponse getBoardView(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구해요 글입니다."));

        User currentUser = userService.getCurrentUser();
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

    public List<BoardListViewResponse> getMyBoards(User user) {
        return boardRepository.findAllByUser(user).stream()
                .map(BoardListViewResponse::new)
                .toList();
    }

    private Device findDeviceByName(String deviceName) {
        return deviceRepository.findByExactDeviceNameWithoutSpacesIgnoreCase(deviceName).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 기기를 찾을 수 없습니다."));
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
