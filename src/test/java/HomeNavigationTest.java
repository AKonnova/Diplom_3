import config.AppUrls;
import io.qameta.allure.Description;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.HomePage;
import pageobject.ProfilePage;
import util.DriverFactory;
import util.NavigationUtils;
import util.api.UserApi;
import util.model.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class HomeNavigationTest {

    private WebDriver driver;
    private HomePage homePage;
    private ProfilePage profilePage;
    private final UserApi userApi = new UserApi();
    private User testUser;
    private String accessToken;
    private final String browser;

    public HomeNavigationTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"chrome"},
                {"firefox"}
        });
    }

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);

        testUser = User.random();
        accessToken = userApi.createAndLoginUserWithRetry(testUser);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Не удалось получить accessToken");
        }

        NavigationUtils.openUrlWithRetry(driver, AppUrls.BASE_URL);
        profilePage.setAccessTokenToLocalStorage(accessToken);
        driver.navigate().refresh();
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
            driver.quit();
        }
    }

    @Test
    @Description("Переход с главной страницы в личный кабинет по клику на 'Личный кабинет'")
    public void testNavigateToPersonalAccount() {
        homePage.clickPersonalAccountButton();
        profilePage.waitForProfilePageToLoad();
        NavigationUtils.assertCurrentUrlEquals(driver, AppUrls.PROFILE_PAGE);
    }

    @Test
    @Description("Переход к разделу 'Булки'")
    public void testNavigateToBunSection() {
        homePage.clickBunTab();
        homePage.assertBunTabIsActive();
    }

    @Test
    @Description("Переход к разделу 'Соусы'")
    public void testNavigateToSauceSection() {
        homePage.clickSauceTab();
        homePage.assertSauceTabIsActive();
    }

    @Test
    @Description("Переход к разделу 'Начинки'")
    public void testNavigateToFillingSection() {
        homePage.clickFillingTab();
        homePage.assertFillingTabIsActive();
    }
}