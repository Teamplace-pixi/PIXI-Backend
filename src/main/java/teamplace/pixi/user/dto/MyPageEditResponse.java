package teamplace.pixi.user.dto;

import lombok.Getter;

@Getter
public class MyPageEditResponse {
    private final int profileId;
    private final String nickname;
    private final String address;
    private final String LoginId;
    private final String password;

    public MyPageEditResponse(int profileId, String nickname, String address, String LoginId, String password) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.address = address;
        this.LoginId = LoginId;
        this.password = password;
    }
}
