package teamplace.pixi.user.contoller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;
import teamplace.pixi.shop.dto.AddShopRequest;
import teamplace.pixi.shop.service.ShopService;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.dto.MyPageEditResponse;
import teamplace.pixi.user.dto.MyPageResponse;
import teamplace.pixi.user.dto.MyPageSetupResponse;
import teamplace.pixi.user.dto.UpdateMyPageRequest;
import teamplace.pixi.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageApiController {

    private final UserService userService;
    private final BoardService boardService;
    private final ShopService shopService;

    @Operation(summary = "마이페이지 정보 조회", description = "현재 로그인한 사용자의 마이페이지 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPage() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new MyPageResponse(
                user.getProfileId(), user.getRollId(), user.getNickname(), user.getAddress()
        ));
    }

    @Operation(summary = "내가 작성한 게시글 조회", description = "로그인한 사용자가 작성한 모든 게시글을 조회합니다.")
    @GetMapping("/boardList")
    public ResponseEntity<List<BoardListViewResponse>> getMyBoards() {
        User user = userService.getCurrentUser();
        List<BoardListViewResponse> boards = boardService.getMyBoards(user);
        return ResponseEntity.ok(boards);
    }

    @Operation(summary = "마이페이지 정보 수정 조회", description = "현재 로그인한 사용자의 마이페이지 정보를 수정할 수 있는 페이지 정보를 반환합니다.")
    @GetMapping("/edit")
    public ResponseEntity<MyPageEditResponse> getMyPageEditPage() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new MyPageEditResponse(
                user.getProfileId(), user.getNickname(), user.getAddress(), user.getLoginId(), user.getPassword()
        ));
    }

    @Operation(summary = "마이페이지 정보 수정", description = "현재 로그인한 사용자의 마이페이지 정보를 수정합니다. 비밀번호 변경 시 현재 비밀번호 확인이 필요합니다.")
    @PutMapping("/edit")
    public ResponseEntity<String> updateMyPage(@RequestBody UpdateMyPageRequest request) {
        userService.updateMyPage(userService.getCurrentUser(), request);
        return ResponseEntity.ok("마이페이지 정보 수정 성공");
    }

    @Operation(summary = "마이페이지 설정", description = "현재 로그인한 사용자의 마이페이지 설정 메뉴 페이지 정보를 반환합니다.")
    @GetMapping("/setup")
    public ResponseEntity<MyPageSetupResponse> getMyPageSetupPage() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new MyPageSetupResponse(
                user.getProfileId(), user.getNickname()
        ));
    }

    @Operation(summary = "마이페이지 수리업체 등록", description = "현재 로그인한 사용자를 일반 유저에서 수리업체로 등록합니다.")
    @PostMapping("/shop")
    public ResponseEntity<?> createShop(
            @RequestPart("shop") AddShopRequest request,
            @RequestPart("shopCertification") MultipartFile certificationFile,
            @RequestPart("thumb") MultipartFile thumbFile
    ) {
        shopService.save(request, certificationFile, thumbFile);
        return ResponseEntity.ok().build();
    }
}
