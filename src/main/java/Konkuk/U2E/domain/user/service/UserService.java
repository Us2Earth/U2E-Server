package Konkuk.U2E.domain.user.service;

import Konkuk.U2E.domain.user.domain.User;
import Konkuk.U2E.domain.user.dto.request.PostUserLoginRequest;
import Konkuk.U2E.domain.user.dto.response.PostUserLoginResponse;
import Konkuk.U2E.domain.user.exception.DuplicateUserException;
import Konkuk.U2E.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.DUPLICATE_USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PostUserLoginResponse signupAndLogin(PostUserLoginRequest req) {
        User existingUser = userRepository.findUserByName(req.name());
        if (existingUser != null) {
            throw new DuplicateUserException(DUPLICATE_USER);
        }

        User newUser = userRepository.save(User.builder()
                .name(req.name())
                .password(req.password())
                .build());

        String token = jwtUtil.generateAccessToken(newUser.getName());
        return PostUserLoginResponse.of(newUser.getUserId(), token);
    }
}
