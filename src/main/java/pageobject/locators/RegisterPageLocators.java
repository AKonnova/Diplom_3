package pageobject.locators;

import org.openqa.selenium.By;

public class RegisterPageLocators {
    public static final By NAME_INPUT = By.xpath("//form//fieldset[1]//input");
    public static final By EMAIL_INPUT = By.xpath("//form//fieldset[2]//input");
    public static final By PASSWORD_INPUT = By.xpath("//form//fieldset[3]//input");
    public static final By REGISTER_BUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    public static final By PASSWORD_ERROR = By.xpath("//p[contains(text(),'Некорректный пароль')]");
    public static final By LOGIN_LINK = By.xpath("//a[@href='/login']");
}