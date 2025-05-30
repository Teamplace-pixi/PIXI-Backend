package teamplace.pixi.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.dto.SignupRequest;
import teamplace.pixi.user.dto.UpdateMyPageRequest;
import teamplace.pixi.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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
                .address(request.getAddress())
                .isSub(false)
                .rollId(0)
                .profileId(0)
                .aiCnt(5)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMyPage(User user, UpdateMyPageRequest request) {
        if (!user.getLoginId().equals(request.getLoginId()) &&
                userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 로그인 ID입니다.");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.updateLoginId(request.getLoginId());
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        user.updateNickname(request.getNickname());
        user.updateAddress(request.getAddress());
        user.updateProfileId(request.getProfileId());
    }

    @Transactional
    public void updateSubscription(User user, boolean isSub) {
        user.updateSubscription(isSub);
        user.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1));
        userRepository.save(user);
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void expireSubscriptions() {
        List<User> expiredUsers = userRepository.findAllBySubscriptionEndDateBefore(LocalDateTime.now());
        for (User user : expiredUsers) {
            user.updateSubscription(false);
        }
        userRepository.saveAll(expiredUsers);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
