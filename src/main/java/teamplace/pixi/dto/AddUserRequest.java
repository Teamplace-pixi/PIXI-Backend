package teamplace.pixi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddUserRequest {
    private String loginId;
    private String password;
    private String nickName;
    private String email;
}
