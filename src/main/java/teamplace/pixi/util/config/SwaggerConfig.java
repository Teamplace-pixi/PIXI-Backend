package teamplace.pixi.util.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("FIXI API")
                .description("FIXI API 목록입니다.");

        return new OpenAPI()
                .info(info);
    }
}