package Konkuk.U2E.domain.user.controller;

import Konkuk.U2E.domain.user.dto.request.PostUserLoginRequest;
import Konkuk.U2E.domain.user.dto.response.PostUserLoginResponse;
import Konkuk.U2E.domain.user.service.UserService;
import Konkuk.U2E.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<PostUserLoginResponse> login(
            @RequestBody PostUserLoginRequest loginRequest
    ) {
        return BaseResponse.ok(userService.signupAndLogin(loginRequest));
    }

}
