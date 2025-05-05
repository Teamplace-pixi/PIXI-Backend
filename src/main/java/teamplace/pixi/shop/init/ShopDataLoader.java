package teamplace.pixi.shop.init;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.repository.ShopRepository;

import java.io.InputStream;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ShopDataLoader {
    private final ShopRepository shopRepository;

    @Bean
    public CommandLineRunner loadShopData(){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/shops.json");
            try{
                List<ShopJson> shops = mapper.readValue(inputStream, new TypeReference<>(){});
                for(ShopJson s : shops){
                    Shop shop = Shop.builder()
                            .userId(s.getUser_id())
                            .shopName(s.getName())
                            .shopLoc(s.getAddr())
                            .shopOpenTime(s.getTime())
                            .shopCall(s.getTel())
                            .shopDetail(s.getShop_detail())
                            .shopCertification(s.getShop_certification())
                            .shopDevice(s.getShop_device())
                            .thumb(s.getThumb())
                            .build();

                    shopRepository.save(shop);
                }
            }catch (Exception e) {
                System.out.println("❌ 데이터 삽입 중 오류: " + e.getMessage());
            }
        };
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ShopJson {
        private Long user_id;
        private String name;
        private String addr;
        private String time;
        private String tel;
        private String shop_detail;
        private String shop_certification;
        private Integer shop_device;
        private String thumb;
        private Integer category;


    }
}



