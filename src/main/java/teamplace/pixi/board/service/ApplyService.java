package teamplace.pixi.board.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.board.domain.Apply;
import teamplace.pixi.board.domain.Board;
import teamplace.pixi.board.dto.ApplyViewResponse;
import teamplace.pixi.board.dto.CheckApplyResponse;
import teamplace.pixi.board.dto.CreateApplyRequest;
import teamplace.pixi.board.repository.ApplyRepository;
import teamplace.pixi.board.repository.BoardRepository;
import teamplace.pixi.matchChat.domain.MatchRoom;
import teamplace.pixi.matchChat.domain.ParticipantType;
import teamplace.pixi.matchChat.dto.MatchChatRequest;
import teamplace.pixi.matchChat.service.MatchChatService;
import teamplace.pixi.matchChat.service.MatchRoomService;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.repository.ShopRepository;
import teamplace.pixi.user.service.UserService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ApplyService {

    private final BoardRepository boardRepository;
    private final ShopRepository shopRepository;
    private final ApplyRepository applyRepository;
    private final MatchRoomService matchRoomService;
    private final MatchChatService matchChatService;
    private final UserService userService;



    @Transactional
    public Apply save(CreateApplyRequest req){
        Long userId = userService.getCurrentUser().getUserId();
        Shop shop = shopRepository.findByUserId(userId);
        if (shop == null) {
            throw new EntityNotFoundException("해당 유저의 수리업체(shop)가 존재하지 않습니다.");
        }
        Board b = boardRepository.findByBoardId(req.getBoardId());
        Shop s = shopRepository.findByUserId(userId);
        Apply apply = Apply.builder()
                .board(b)
                .shop(s)
                .applyCost(req.getApplyCost())
                .applyDate(req.getApplyDate())
                .applyContent(req.getApplyContent())
                .applyAt(LocalDateTime.now())
                .build();

        Long roomId = matchRoomService.createOrFindMatchRoom(b.getUser().getUserId(), s.getShopId()); // board 작성자 + 지원자 id 넘겨주기
        Apply newapply = applyRepository.save(apply); // 지원서 저장
        MatchChatRequest msg = MatchChatRequest.builder()
                .roomId(roomId)
                .message(String.format("{\"applyId\": %d, \"boardTitle\": \"%s\", \"title\": \"%s\", \"boardId\": %d,}",
                        newapply.getApplyId(), b.getBoardTitle(), "수리 지원", b.getBoardId()))
                .senderId(userId)
                .receiverId(b.getUser().getUserId())
                .build();

        matchChatService.sendMessage(msg, "info");

        return newapply;
    }

    public ApplyViewResponse getApplyView(Long applyId){
        Apply apply = applyRepository.findById(applyId).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 지원서입니다"));
        Shop shop = apply.getShop();
        return new ApplyViewResponse(apply, shop);
    }

    public CheckApplyResponse hasapplied(Long boardId){
        Long userId = userService.getCurrentUser().getUserId();
        Shop shop = shopRepository.findByUserId(userId);
        if (shop == null) {
            throw new EntityNotFoundException("해당 유저의 수리업체(shop)가 존재하지 않습니다.");
        }

        Board board = boardRepository.findByBoardId(boardId);

        Apply apply = applyRepository.findByBoardAndShop(board, shop).orElse(null);
        return new CheckApplyResponse(apply != null ? apply.getApplyId() : null);

    }

}
