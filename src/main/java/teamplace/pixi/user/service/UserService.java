package teamplace.pixi.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.dto.SignupRequest;
import teamplace.pixi.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickname(request.getNickname())
                .isSub(false)
                .rollId(1)
                .profileId(1)
                .aiCnt(5)
                .build();

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}

