package teamplace.pixi.error;

public class DuplicateLoginIdException extends RuntimeException {
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
