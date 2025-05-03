package teamplace.pixi.Device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public List<Device> searchDevicesByName(String keyword) {
        return deviceRepository.findByNameLike(keyword);
    }
}
