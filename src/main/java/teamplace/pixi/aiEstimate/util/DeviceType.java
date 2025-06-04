package teamplace.pixi.aiEstimate.util;

import lombok.Getter;

@Getter
public enum DeviceType {
    MOBILE(0, "핸드폰"),
    ACCESSORY(1, "악세사리");

    private final int code;
    private final String label;

    DeviceType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static DeviceType fromCode(int code) {
        for (DeviceType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("알 수 없는 디바이스 코드입니다: " + code);
    }
}
