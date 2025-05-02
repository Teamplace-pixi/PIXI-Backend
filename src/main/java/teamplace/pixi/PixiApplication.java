package teamplace.pixi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PixiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PixiApplication.class, args);
        System.out.println("test");
    }
}
