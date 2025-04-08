package teamplace.pixi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import teamplace.pixi.domain.User;
import teamplace.pixi.dto.AddUserRequest;
import teamplace.pixi.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getLogin_id())
                .password(encoder.encode(dto.getPassword()))
                .build()).getUser_id();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));
    }

    public User findByLoginId(String login_id) {
        return userRepository.findByLoginId(login_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));
    }
}
