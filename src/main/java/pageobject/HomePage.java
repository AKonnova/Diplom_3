package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.locators.HomePageLocators;
import util.WebElementInteractions;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной странице")
    public void clickLoginButton() {
        WebElementInteractions.clickElement(driver, HomePageLocators.LOGIN_BUTTON);
    }

    @Step("Нажать кнопку 'Личный кабинет' на главной странице")
    public void clickPersonalAccountButton() {
        WebElementInteractions.clickElement(driver, HomePageLocators.PERSONAL_ACCOUNT_BUTTON);
    }

    @Step("Проверить, что пользователь вошел (кнопка 'Оформить заказ' видна)")
    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageLocators.ORDER_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Убедиться, что кнопка 'Оформить заказ' отображается")
    public void assertOrderButtonVisible() {
        if (!isUserLoggedIn()) {
            throw new AssertionError("Кнопка 'Оформить заказ' не найдена. Пользователь не авторизован.");
        }
    }

    @Step("Клик по вкладке 'Булки'")
    public void clickBunTab() {
        WebElementInteractions.clickElement(driver, HomePageLocators.BUN_TAB);
    }

    @Step("Клик по вкладке 'Соусы'")
    public void clickSauceTab() {
        WebElementInteractions.clickElement(driver, HomePageLocators.SAUCE_TAB);
    }

    @Step("Клик по вкладке 'Начинки'")
    public void clickFillingTab() {
        WebElementInteractions.clickElement(driver, HomePageLocators.FILLING_TAB);
    }

    @Step("Проверка, что активна вкладка '{tabName}'")
    private void assertTabIsActive(WebElement tabElement, String tabName) {
        String classAttr = tabElement.getAttribute("class");

        if (classAttr == null || !classAttr.contains(HomePageLocators.ACTIVE_TAB_CLASS)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String classAttrAfterWait = tabElement.getAttribute("class");
            if (classAttrAfterWait == null || !classAttrAfterWait.contains(HomePageLocators.ACTIVE_TAB_CLASS)) {
                throw new AssertionError("Вкладка '" + tabName + "' не активна. Атрибут class: '" + classAttrAfterWait + "'");
            }
        }
    }

    @Step("Проверка, что активна вкладка 'Булки'")
    public void assertBunTabIsActive() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(HomePageLocators.BUN_TAB));
        assertTabIsActive(tab, "Булки");
    }

    @Step("Проверка, что активна вкладка 'Соусы'")
    public void assertSauceTabIsActive() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(HomePageLocators.SAUCE_TAB));
        assertTabIsActive(tab, "Соусы");
    }

    @Step("Проверка, что активна вкладка 'Начинки'")
    public void assertFillingTabIsActive() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(HomePageLocators.FILLING_TAB));
        assertTabIsActive(tab, "Начинки");
    }
}