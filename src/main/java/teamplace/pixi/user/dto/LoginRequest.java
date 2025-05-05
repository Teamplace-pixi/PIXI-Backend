package teamplace.pixi.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
    private String loginId;
    private String password;
}
