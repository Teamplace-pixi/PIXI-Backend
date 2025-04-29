package teamplace.pixi.user.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.user.dto.SignupRequest;
import teamplace.pixi.user.dto.LoginRequest;
import teamplace.pixi.util.error.SuccessResponse;
import teamplace.pixi.util.error.ErrorResponse;
import teamplace.pixi.user.error.UserException;
import teamplace.pixi.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 ID입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            userService.save(request);
            return ResponseEntity.ok(new SuccessResponse("회원가입 성공"));
        }
        catch (UserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("409", e.getMessage()));
        }
    }

    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            userService.login(request);
            return ResponseEntity.ok(new SuccessResponse("로그인 성공"));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("404", e.getMessage()));
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 수행합니다.")
    @GetMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new SuccessResponse("로그아웃 성공"));
    }
}