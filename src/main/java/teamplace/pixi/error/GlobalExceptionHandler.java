package teamplace.pixi.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateLoginIdException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateLoginIdException(DuplicateLoginIdException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "409");
        errorResponse.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
