package teamplace.pixi.matchChat.messagingrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "teamplace.pixi")
public class MessagingRabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessagingRabbitmqApplication.class, args);
    }
}
