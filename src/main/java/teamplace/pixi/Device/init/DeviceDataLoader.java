package teamplace.pixi.Device.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;

import java.io.InputStream;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DeviceDataLoader {
    private final DeviceRepository deviceRepository;

    @Bean
    public CommandLineRunner loadDeviceData() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/디바이스_전체_한국어_변환.json");
            try {
                List<DeviceJson> devices = mapper.readValue(inputStream, new TypeReference<>() {});
                for (DeviceJson d : devices) {
                    Device device = new Device(
                            d.get이름(),
                            d.get기기유형코드(),
                            d.get이미지(),
                            d.get브랜드()
                    );
                    deviceRepository.save(device);
                }
                System.out.println("✅ 디바이스 초기 데이터 삽입 완료");
            } catch (Exception e) {
                System.out.println("❌ 데이터 삽입 중 오류: " + e.getMessage());
            }
        };
    }


    private static class DeviceJson {
        private String 이름;
        private String 브랜드;
        private Integer 기기유형코드;
        private String 이미지;

        public String get이름() { return 이름; }
        public String get브랜드() { return 브랜드; }
        public Integer get기기유형코드() { return 기기유형코드; }
        public String get이미지() { return 이미지; }
    }
}
