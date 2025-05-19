package teamplace.pixi.user.dto;

import lombok.Getter;

@Getter
public class MyPageSetupResponse {
    private final int profileId;
    private final String nickname;

    public MyPageSetupResponse(int profileId, String nickname) {
        this.profileId = profileId;
        this.nickname = nickname;
    }
}
