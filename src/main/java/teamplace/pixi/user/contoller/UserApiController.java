package teamplace.pixi.user.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import teamplace.pixi.user.dto.LoginResponse;
import teamplace.pixi.user.dto.SignupRequest;
import teamplace.pixi.user.dto.LoginRequest;
import teamplace.pixi.user.repository.UserRepository;
import teamplace.pixi.util.error.SuccessResponse;
import teamplace.pixi.util.error.ErrorResponse;
import teamplace.pixi.user.service.UserService;
import teamplace.pixi.util.jwt.JwtUtil;
import teamplace.pixi.util.jwt.WebSocketJwt;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final WebSocketJwt webSocketJwt;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 회원 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 인증 객체 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword())
        );

        // 인증이 성공하면 JWT 토큰을 생성
        String token = jwtUtil.createToken(authentication.getName());
        Long userId = userService.getUserId(authentication.getName());
        String tokenWs = webSocketJwt.createWSToken(userId);

        // 로그인 성공 후 JWT 토큰 반환
        return ResponseEntity.ok(new LoginResponse(token, tokenWs));
    }

}