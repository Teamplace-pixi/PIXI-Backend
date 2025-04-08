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
    public User loadUserByUsername(String login_id) {
        return userRepository.findByLoginId(login_id)
                .orElseThrow(() -> new IllegalArgumentException(login_id));
    }
}
