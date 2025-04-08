package teamplace.pixi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String login_id;
    private String password;
    private String name;
    private String email;
}
