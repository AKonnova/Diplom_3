package pageobject.locators;

import org.openqa.selenium.By;

public class HomePageLocators {
    public static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти в аккаунт']");
    public static final By PERSONAL_ACCOUNT_BUTTON = By.xpath("//p[text()='Личный Кабинет']");
    public static final By ORDER_BUTTON = By.xpath("//button[text()='Оформить заказ']");
    public static final By BUN_TAB = By.xpath("//div[contains(@class,'tab_tab') and .//span[text()='Булки']]");
    public static final By SAUCE_TAB = By.xpath("//div[contains(@class,'tab_tab') and .//span[text()='Соусы']]");
    public static final By FILLING_TAB = By.xpath("//div[contains(@class,'tab_tab') and .//span[text()='Начинки']]");
    public static final String ACTIVE_TAB_CLASS = "tab_tab_type_current__2BEPc";
}