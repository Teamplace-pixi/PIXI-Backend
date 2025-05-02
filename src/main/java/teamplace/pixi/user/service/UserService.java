package teamplace.pixi.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.dto.SignupRequest;
import teamplace.pixi.user.dto.LoginRequest;
import teamplace.pixi.user.error.UserException;
import teamplace.pixi.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long save(SignupRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 중복 로그인 ID 체크
        if (userRepository.existsByLoginId(dto.getLoginId())) {
            throw new UserException("이미 존재하는 ID입니다.");
        }

        // 중복 email 체크
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserException("이미 존재하는 email입니다.");
        }

        // 중복 nickname 체크
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new UserException("이미 존재하는 이름입니다.");
        }

        return userRepository.save(User.builder()
                .loginId(dto.getLoginId())
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .isSub(false)
                .rollId(0)
                .profileId(0)
                .aiCnt(5)
                .build()).getUserId();
    }

    @Transactional(readOnly = true)
    public void login(LoginRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new UserException("존재하지 않는 회원 정보입니다."));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("존재하지 않는 회원 정보입니다.");
        }

        // JWT 발급 or 세션 처리 등은 여기에 추가
    }
}

