package teamplace.pixi.Device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.dto.PartListViewResponse;
import teamplace.pixi.Device.repository.DeviceRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public List<Device> searchDevicesByName(String keyword) {
        return deviceRepository.findByNameLike(keyword);
    }

    public PartListViewResponse getPartListView(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기입니다."));
        return new PartListViewResponse(device);
    }

    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기입니다."));
    }

    public List<String> getDeviceCategory(Long deviceId){
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기입니다."));

        String device_maker = device.getDeviceBrand();
        Integer deviceTypeCode = device.getDeviceType();  // ex: 0, 1, 2, 3

        String deviceType;

        switch (deviceTypeCode) {
            case 0 -> deviceType = "핸드폰";
            case 1 -> deviceType = "노트북";
            case 2 -> deviceType = "패드";
            case 3 -> deviceType = "악세사리";
            default -> throw new IllegalArgumentException("알 수 없는 기기 타입입니다.");
        }

        return List.of(device_maker, deviceType);

    }
}
