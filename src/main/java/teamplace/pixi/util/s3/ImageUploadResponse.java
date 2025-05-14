package teamplace.pixi.util.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageUploadResponse {
    private Long imageId;
    private String fileUrl;
}