package teamplace.pixi.Device.dto;

import lombok.Getter;
import teamplace.pixi.Device.domain.Device;

@Getter
public class PartListViewResponse {
    private final int displayPrice;
    private final int backPrice;
    private final int cameraPrice;
    private final int batteryPrice;
    private final int mainPrice;

    public PartListViewResponse(Device device) {
        this.displayPrice = device.getDisplayPrice();
        this.backPrice = device.getBackPrice();
        this.cameraPrice = device.getCameraPrice();
        this.batteryPrice = device.getBatteryPrice();
        this.mainPrice = device.getMainPrice();
    }
}
