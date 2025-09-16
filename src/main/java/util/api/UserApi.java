package util.api;

import config.AppUrls;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import util.model.User;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class UserApi {

    private static final String BASE_URL = AppUrls.BASE_URL;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;

    @Step("Создание пользователя через API")
    public ValidatableResponse createUser(User user) {
        return given()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Создание пользователя через API с повторными попытками")
    public ValidatableResponse createUserWithRetry(User user) {
        return RetryUtils.withRetry(() -> {
            ValidatableResponse response = createUser(user);
            int statusCode = response.extract().statusCode();
            if (statusCode != SC_OK) {
                throw new RuntimeException("Ошибка создания пользователя. Код ответа: " + statusCode);
            }
            return response;
        }, MAX_RETRIES, RETRY_DELAY_MS);
    }

    @Step("Создание и авторизация пользователя через API с повторными попытками")
    public String createAndLoginUserWithRetry(User user) {
        return RetryUtils.withRetry(() -> {
            createUserWithRetry(user);
            return loginAndGetTokenWithRetry(user);
        }, MAX_RETRIES, RETRY_DELAY_MS);
    }

    @Step("Авторизация пользователя и получение accessToken (также сохраняется refreshToken)")
    public String loginAndGetToken(User user) {
        ValidatableResponse response = given()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then();

        int statusCode = response.extract().statusCode();

        if (statusCode == SC_OK) {
            String accessToken = response.extract().path("accessToken");
            String refreshToken = response.extract().path("refreshToken");

            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);

            return accessToken;
        } else {
            throw new RuntimeException("Не удалось получить accessToken. Код ответа: " + statusCode);
        }
    }

    @Step("Авторизация пользователя и получение accessToken с повторными попытками")
    public String loginAndGetTokenWithRetry(User user) {
        return RetryUtils.withRetry(() -> loginAndGetToken(user), MAX_RETRIES, RETRY_DELAY_MS);
    }

    @Step("Удаление пользователя по accessToken")
    public ValidatableResponse deleteUserByAccessToken(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then();
    }
}