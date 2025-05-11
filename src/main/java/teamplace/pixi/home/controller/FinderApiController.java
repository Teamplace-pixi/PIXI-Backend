package teamplace.pixi.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.Device.dto.PartListViewResponse;
import teamplace.pixi.Device.service.DeviceService;
import teamplace.pixi.board.dto.BoardListViewResponse;
import teamplace.pixi.board.service.BoardService;
import teamplace.pixi.shop.dto.ShopListViewResponse;
import teamplace.pixi.shop.service.ShopService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finder")
public class FinderApiController {
    private final BoardService boardService;
    private final DeviceService deviceService;
    private final ShopService shopService;

    @Operation(summary = "기기별 부품 가격 목록", description = "기기 아이디로 부품 가격 목록을 조회합니다")
    @GetMapping("/partList/device_id={deviceId}")
    public PartListViewResponse getPartList(@PathVariable("deviceId") Long deviceId) {
        return deviceService.getPartListView(deviceId);
    }

    @Operation(summary = "기기별 수리업체 목록", description = "기기 아이디로 수리 가능 업체 목록을 조회합니다")
    @GetMapping("/shopList/device_id={deviceId}")
    public ResponseEntity<List<ShopListViewResponse>> getShopListByDeviceId(@PathVariable("deviceId") Long deviceId) {
        List<ShopListViewResponse> shopList = shopService.getShopsListByDeviceId(deviceId);
        return ResponseEntity.ok(shopList);
    }

    @Operation(summary = "기기별 구해요 목록", description = "기기 아이디로 모집 중인 구해요 목록을 조회합니다")
    @GetMapping("/boardList/device_id={deviceId}")
    public ResponseEntity<List<BoardListViewResponse>> getBoardsByDeviceId(@PathVariable("deviceId") Long deviceId) {
        List<BoardListViewResponse> boards = boardService.getBoardsByDeviceId(deviceId);
        return ResponseEntity.ok(boards);
    }
}
