package teamplace.pixi.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private String address;
}
