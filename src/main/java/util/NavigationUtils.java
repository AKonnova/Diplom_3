package util;

import io.qameta.allure.Step;
import util.api.RetryUtils;
import org.openqa.selenium.WebDriver;

public class NavigationUtils {

    @Step("Открываем URL с повторами: {url}")
    public static void openUrlWithRetry(WebDriver driver, String url) {
        RetryUtils.withRetry(() -> {
            driver.get(url);
            return null;
        });
    }

    @Step("Проверяем, что текущий URL равен ожидаемому: {expectedUrl}")
    public static void assertCurrentUrlEquals(WebDriver driver, String expectedUrl) {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.startsWith(expectedUrl)) {
            throw new AssertionError("Ожидался переход на страницу " + expectedUrl + ", но текущий URL: " + currentUrl);
        }
    }
}