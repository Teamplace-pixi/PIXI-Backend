package teamplace.pixi.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import teamplace.pixi.user.service.UserDetailService;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null && jwtUtil.validateToken(token)) {  // 토큰이 유효하면
            String username = jwtUtil.extractUsername(token);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);  // 사용자 정보 로드
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()  // 권한 정보 설정
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);  // 인증 정보 설정
            }
        }

        filterChain.doFilter(request, response);  // 필터 체인 계속 실행
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);  // Authorization 헤더에서 토큰 추출
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // "Bearer "를 제거하고 토큰만 추출
        }
        return null;
    }
}
