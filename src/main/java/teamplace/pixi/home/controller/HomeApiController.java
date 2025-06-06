package teamplace.pixi.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.dto.CreateApplyRequest;
import teamplace.pixi.board.service.BoardService;
import teamplace.pixi.home.controller.dto.TokenRequest;
import teamplace.pixi.home.controller.dto.TokenResponse;
import teamplace.pixi.shop.dto.ShopListViewResponse;
import teamplace.pixi.shop.service.ShopService;
import teamplace.pixi.user.service.UserService;
import teamplace.pixi.util.jwt.WebSocketJwt;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeApiController {
    private final BoardService boardService;
    private final ShopService shopService;
    private final AuthenticationManager authenticationManager;


    @Operation(summary = "홈", description = "홈 진입점")
    @GetMapping("/home")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

    @Operation(summary = "홈 구해요 목록", description = "홈 화면에서 최신 구해요 글 10개를 조회합니다")
    @GetMapping("/home/board")
    public ResponseEntity<List<BoardListViewResponse>> getHomeBoardList() {
        List<BoardListViewResponse> latestBoards = boardService.getLatestBoards();
        return ResponseEntity.ok(latestBoards);
    }

    @Operation(summary = "홈 숨은 맛집 목록", description = "홈 화면에서 후기 높은 수리업체 10개를 조회합니다")
    @GetMapping("home/shop")
    public ResponseEntity<List<ShopListViewResponse>> getHomeShopList() {
        List<ShopListViewResponse> latestShops = shopService.getShopListAtHome();
        return ResponseEntity.ok(latestShops);
    }

}
