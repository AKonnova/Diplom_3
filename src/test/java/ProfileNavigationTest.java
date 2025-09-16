import config.AppUrls;
import io.qameta.allure.Description;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.ProfilePage;
import util.DriverFactory;
import util.NavigationUtils;
import util.api.UserApi;
import util.model.User;

import java.time.Duration;

public class ProfileNavigationTest {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private final UserApi userApi = new UserApi();
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        testUser = User.random();

        accessToken = userApi.createAndLoginUserWithRetry(testUser);
        if (accessToken == null || accessToken.isEmpty()) {
            if (driver != null) driver.quit();
            throw new RuntimeException("Не удалось получить accessToken");
        }

        NavigationUtils.openUrlWithRetry(driver, AppUrls.BASE_URL);

        profilePage.setAccessTokenToLocalStorage(accessToken);

        if (testUser.getRefreshToken() != null && !testUser.getRefreshToken().isEmpty()) {
            profilePage.setRefreshTokenToLocalStorage(testUser.getRefreshToken());
        }

        driver.navigate().refresh();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        homePage.clickPersonalAccountButton();

        profilePage.waitForProfilePageToLoad();

        String currentUrl = driver.getCurrentUrl().trim();
        if (!currentUrl.equals(AppUrls.PROFILE_PAGE)) {
            if (driver != null) driver.quit();
            throw new AssertionError("Ожидалась страница профиля, но URL: " + currentUrl);
        }
    }

    @After
    public void tearDown() {
        if (testUser != null && accessToken != null) {
            try {
                userApi.deleteUserByAccessToken(accessToken);
            } catch (Exception e) {
                System.err.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }

    @Test
    @Description("Переход из профиля в конструктор через кнопку 'Конструктор'")
    public void navigateFromProfileToConstructor() {
        profilePage.clickConstructorButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> {
            String url = d.getCurrentUrl().trim();
            boolean urlIsBase = AppUrls.BASE_URL.equals(url);
            boolean orderButtonVisible = false;
            try {
                orderButtonVisible = homePage.isUserLoggedIn();
            } catch (Exception e) {
                // ignore
            }
            return urlIsBase || orderButtonVisible;
        });

        homePage.assertOrderButtonVisible();
    }

    @Test
    @Description("Переход из профиля в конструктор через клик по логотипу")
    public void navigateFromProfileToConstructorViaLogo() {
        profilePage.clickLogo();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> {
            String url = d.getCurrentUrl().trim();
            boolean urlIsBase = AppUrls.BASE_URL.equals(url);
            boolean orderButtonVisible = false;
            try {
                orderButtonVisible = homePage.isUserLoggedIn();
            } catch (Exception e) {
                // ignore
            }
            return urlIsBase || orderButtonVisible;
        });

        homePage.assertOrderButtonVisible();
    }

    @Test
    @Description("Выход из аккаунта через кнопку 'Выйти' в личном кабинете")
    public void logoutFromProfile() {
        profilePage.clickLogoutButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> {
            try {
                loginPage.assertLoginButtonVisible();
                return true;
            } catch (AssertionError | Exception e) {
                return false;
            }
        });

        String currentUrl = driver.getCurrentUrl().trim();
        if (!currentUrl.endsWith("/login")) {
            throw new AssertionError("После выхода URL не заканчивается на /login. Текущий URL: " + currentUrl);
        }
    }
}