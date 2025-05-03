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


    public Device(String deviceName, Integer deviceType, String deviceImg, String deviceBrand) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.deviceImg = deviceImg;
        this.deviceBrand = deviceBrand;
    }

    public Long getDeviceId() {
        return deviceId;
    }


    public String getDeviceName() {
        return deviceName;
    }


    public Integer getDeviceType() {
        return deviceType;
    }



    public String getDeviceImg() {
        return deviceImg;
    }


    public String getDeviceBrand() {
        return deviceBrand;
    }



}
