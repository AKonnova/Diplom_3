package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final int MAX_RETRIES = 2;

    public static WebDriver getDriver() {
        String browserName = System.getProperty("browser", "chrome");
        return createDriverWithRetry(browserName);
    }

    private static WebDriver createDriverWithRetry(String browserName) {
        int attempt = 0;
        while (attempt <= MAX_RETRIES) {
            int currentAttempt = attempt + 1;
            try {
                Allure.step("Попытка запуска WebDriver №" + currentAttempt + " для браузера: " + browserName);
                WebDriver driver = createDriverInstance(browserName);
                Allure.step("Успешный запуск WebDriver на попытке №" + currentAttempt);
                return driver;
            } catch (Exception e) {
                Allure.step("Ошибка запуска WebDriver на попытке №" + currentAttempt + " для " + browserName + ": " + e.getClass().getSimpleName() + " — " + e.getMessage());
                attempt++;
                if (attempt > MAX_RETRIES) {
                    throw new RuntimeException("Не удалось запустить WebDriver для " + browserName + " после " + (MAX_RETRIES + 1) + " попыток", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Ожидание между попытками было прервано", ie);
                }
            }
        }
        throw new IllegalStateException("Неожиданная ошибка в логике retry для " + browserName);
    }

    private static WebDriver createDriverInstance(String browserName) {
        switch (browserName.toLowerCase()) {
            case "firefox":
                return createFirefoxDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        Allure.step("Starting ChromeDriver");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        Allure.step("Starting FirefoxDriver");
        return new FirefoxDriver(options);
    }
}