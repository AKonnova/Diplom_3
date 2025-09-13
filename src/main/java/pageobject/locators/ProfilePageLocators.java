package pageobject.locators;

import org.openqa.selenium.By;

public class ProfilePageLocators {
    public static final By PROFILE_HEADER = By.linkText("Профиль");
    public static final By CONSTRUCTOR_BUTTON = By.xpath("//a[contains(., 'Конструктор')]");
    public static final By LOGO_STELLAR_BURGERS = By.className("AppHeader_header__logo__2D0X2");
    public static final By LOGOUT_BUTTON = By.xpath("//button[contains(text(), 'Выход') and contains(@class, 'Account_button')]");
}