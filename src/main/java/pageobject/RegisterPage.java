package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.locators.RegisterPageLocators;
import util.WebElementInteractions;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Вводим имя: {name}")
    public void fillName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.NAME_INPUT));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    @Step("Вводим email: {email}")
    public void fillEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.EMAIL_INPUT));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Вводим пароль: {password}")
    public void fillPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Нажимаем кнопку 'Зарегистрироваться'")
    public void submit() {
        WebElementInteractions.clickElement(driver, RegisterPageLocators.REGISTER_BUTTON);
    }

    @Step("Нажимаем ссылку 'Войти'")
    public void clickLoginLink() {
        WebElementInteractions.clickElement(driver, RegisterPageLocators.LOGIN_LINK);
    }
}