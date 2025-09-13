package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageobject.locators.ForgotPasswordLocators;
import util.WebElementInteractions; // Импортируем новый класс

import java.time.Duration;

public class ForgotPasswordPage {
    private final WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажимаем ссылку 'Войти'")
    public void clickLoginLink() {
        WebElementInteractions.clickElement(driver, ForgotPasswordLocators.LOGIN_LINK);
    }
}