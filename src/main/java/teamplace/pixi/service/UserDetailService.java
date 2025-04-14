package teamplace.pixi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import teamplace.pixi.domain.User;
import teamplace.pixi.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public User loadUserByUsername(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException( "존재하지 않는 회원 정보입니다." + (loginId)));
    }
}
