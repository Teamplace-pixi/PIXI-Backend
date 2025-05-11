package teamplace.pixi.user.dto;

import lombok.Getter;

@Getter
public class MyPageResponse {
    private final int profileId;
    private final int rollId;
    private final String nickname;
    private final String address;

    public MyPageResponse(int profileId, int rollId, String nickname, String address) {
        this.profileId = profileId;
        this.rollId = rollId;
        this.nickname = nickname;
        this.address = address;
    }
}
