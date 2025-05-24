package teamplace.pixi.util.paypal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "paypal")
@Getter
@Setter
public class PayPalProperties {
    private String clientId;
    private String clientSecret;
}

