package tests;

import config.AppUrls;
import io.qameta.allure.Description;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobject.*;
import util.DriverFactory;
import util.NavigationUtils;
import util.api.UserApi;
import util.model.User;

public class LoginTests {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private UserApi userApi = new UserApi();
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        testUser = User.random();
        userApi.createUserWithRetry(testUser);
        NavigationUtils.openUrlWithRetry(driver, AppUrls.BASE_URL);
    }

    @After
    public void tearDown() {
        if (testUser != null && accessToken != null && !accessToken.isEmpty()) {
            try {
                userApi.deleteUserByAccessToken(accessToken);
            } catch (Exception e) {
                System.err.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Вход в систему через кнопку «Войти в аккаунт» на главной странице")
    public void loginViaMainLoginButton() {
        homePage.clickLoginButton();
        loginPage.assertOnLoginPage();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        accessToken = userApi.loginAndGetTokenWithRetry(testUser);

        homePage.assertOrderButtonVisible();
    }

    @Test
    @Description("Вход в систему через кнопку «Личный кабинет» на главной странице")
    public void loginViaPersonalAccountButton() {
        homePage.clickPersonalAccountButton();
        loginPage.assertOnLoginPage();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        accessToken = userApi.loginAndGetTokenWithRetry(testUser);

        homePage.assertOrderButtonVisible();
    }

    @Test
    @Description("Вход в систему через кнопку «Войти» на странице регистрации")
    public void loginViaRegisterPage() {
        NavigationUtils.openUrlWithRetry(driver, AppUrls.REGISTER_PAGE);
        registerPage.clickLoginLink();
        loginPage.assertOnLoginPage();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        accessToken = userApi.loginAndGetTokenWithRetry(testUser);

        homePage.assertOrderButtonVisible();
    }

    @Test
    @Description("Вход в систему через кнопку «Войти» на странице восстановления пароля")
    public void loginViaForgotPasswordPage() {
        NavigationUtils.openUrlWithRetry(driver, AppUrls.FORGOT_PASSWORD_PAGE);
        forgotPasswordPage.clickLoginLink();
        loginPage.assertOnLoginPage();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        accessToken = userApi.loginAndGetTokenWithRetry(testUser);

        homePage.assertOrderButtonVisible();
    }
}