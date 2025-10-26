package Konkuk.U2E.domain.user.controller;

import Konkuk.U2E.domain.user.dto.request.PostUserLoginRequest;
import Konkuk.U2E.domain.user.dto.response.PostUserLoginResponse;
import Konkuk.U2E.domain.user.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService; // mock
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        RestAssuredMockMvc.standaloneSetup(userController);
    }

    @AfterEach
    void tearDown() {
        RestAssuredMockMvc.reset();
    }

    @Test
    @DisplayName("POST /user/login 성공 - BaseResponse 포맷(success, code, message, data) 검증")
    void login_success() {
        // given
        long userId = 1L;
        String token = "mock-jwt-token";
        when(userService.signupAndLogin(any(PostUserLoginRequest.class)))
                .thenReturn(PostUserLoginResponse.of(userId, token));

        // when & then
        RestAssuredMockMvc
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("""
                      {
                        "name": "alice",
                        "password": "password123"
                      }
                      """)
                .when()
                .post("/user/login")
                .then()
                .statusCode(200)
                // BaseResponse 공통 필드
                .body("success", Matchers.is(true))
                .body("code", Matchers.notNullValue())
                .body("message", Matchers.notNullValue())
                // data 내부 필드
                .body("data", Matchers.notNullValue())
                .body("data.userId", Matchers.equalTo((int) userId))
                .body("data.token", Matchers.equalTo(token));
    }
}