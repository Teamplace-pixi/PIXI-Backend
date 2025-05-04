package teamplace.pixi.Device.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "device_type", nullable = false)
    private Integer deviceType;

    @Column(name = "device_img")
    private String deviceImg;

    @Column(name = "device_brand")
    private String deviceBrand;

    @Column(name = "display_price")
    private int displayPrice;

    @Column(name = "back_price")
    private int backPrice;

    @Column(name = "camera_price")
    private int cameraPrice;

    @Column(name = "battery_price")
    private int batteryPrice;

    @Column(name = "main_price")
    private int mainPrice;


    public Device(String deviceName, Integer deviceType, String deviceImg, String deviceBrand,
                  int displayPrice, int backPrice, int cameraPrice, int batteryPrice, int mainPrice) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.deviceImg = deviceImg;
        this.deviceBrand = deviceBrand;
        this.displayPrice = displayPrice;
        this.backPrice = backPrice;
        this.cameraPrice = cameraPrice;
        this.batteryPrice = batteryPrice;
        this.mainPrice = mainPrice;
    }
}
