package pageobject;

import config.AppUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.locators.LoginPageLocators;
import util.WebElementInteractions;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Вводим email: {email}")
    public void fillEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageLocators.EMAIL_INPUT));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Вводим пароль: {password}")
    public void fillPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageLocators.PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Нажимаем кнопку 'Войти'")
    public void submit() {
        WebElementInteractions.clickElement(driver, LoginPageLocators.LOGIN_BUTTON);
    }

    @Step("Нажимаем ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        WebElementInteractions.clickElement(driver, LoginPageLocators.REGISTER_LINK);
    }

    @Step("Нажимаем ссылку 'Забыли пароль?'")
    public void clickForgotPasswordLink() {
        WebElementInteractions.clickElement(driver, LoginPageLocators.FORGOT_PASSWORD_LINK);
    }

    @Step("Ожидаем переход и проверяем, что на странице логина")
    public void assertOnLoginPage() {
        wait.until(ExpectedConditions.urlToBe(AppUrls.LOGIN_PAGE));

        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.equals(AppUrls.LOGIN_PAGE)) {
            throw new AssertionError("Ожидалась страница логина, но URL: " + currentUrl);
        }
    }

    @Step("Убедиться, что кнопка 'Войти в аккаунт' видна на странице логина")
    public void assertLoginButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageLocators.LOGIN_BUTTON));
        } catch (Exception e) {
            throw new AssertionError("Кнопка 'Войти в аккаунт' не отображается на странице логина");
        }
    }

    @Step("Логинимся с email: {email}")
    public void login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        submit();
    }
}