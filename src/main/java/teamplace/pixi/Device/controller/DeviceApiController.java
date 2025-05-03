package teamplace.pixi.Device.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.service.DeviceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceApiController {
    private final DeviceService deviceService;
    @Operation(summary = "디바이스 이름 검색", description = "디바이스를 이름으로 검색합니다.")
    @GetMapping("/search")
    public List<Device> searchDevices(@RequestParam("name") String name) {
        return deviceService.searchDevicesByName(name);
    }
}
