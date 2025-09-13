import io.qameta.allure.Description;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import util.DriverFactory;
import config.AppUrls;
import util.api.UserApi;
import util.model.User;
import util.NavigationUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegisterTests {

    private WebDriver driver;
    private final String browser;
    private User testUser;
    private final UserApi userApi = new UserApi();

    public RegisterTests(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"firefox"}
        });
    }

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @After
    public void tearDown() {
        if (testUser != null) {
            try {
                String token = userApi.loginAndGetTokenWithRetry(testUser);
                if (token != null && !token.isEmpty()) {
                    userApi.deleteUserByAccessToken(token);
                }
            } catch (Exception e) {
                System.err.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Регистрация пользователя с уникальными данными")
    public void registerNewUser() {
        RegisterPage registerPage = new RegisterPage(driver);

        NavigationUtils.openUrlWithRetry(driver, AppUrls.REGISTER_PAGE);
        testUser = User.random();

        registerPage.fillName(testUser.getName());
        registerPage.fillEmail(testUser.getEmail());
        registerPage.fillPassword(testUser.getPassword());
        registerPage.submit();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.assertOnLoginPage();
        loginPage.assertLoginButtonVisible();
    }
}