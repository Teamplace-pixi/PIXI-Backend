package teamplace.pixi.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMyPageRequest {
    private String loginId;
    private String currentPassword;
    private String newPassword;
    private String nickname;
    private String address;
    private int profileId;
}