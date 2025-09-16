package pageobject.locators;

import org.openqa.selenium.By;

public class LoginPageLocators {
    public static final By EMAIL_INPUT = By.xpath("//input[@name='name']");
    public static final By PASSWORD_INPUT = By.xpath("//input[@name='Пароль']");
    public static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти']");
    public static final By REGISTER_LINK = By.xpath("//a[@href='/register']");
    public static final By FORGOT_PASSWORD_LINK = By.xpath("//a[@href='/forgot-password']");
}