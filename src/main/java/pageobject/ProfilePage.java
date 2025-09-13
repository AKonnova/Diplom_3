package pageobject;

import config.AppUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.locators.ProfilePageLocators;
import util.WebElementInteractions;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePageToLoad() {
        wait.until(ExpectedConditions.urlToBe(AppUrls.PROFILE_PAGE));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProfilePageLocators.PROFILE_HEADER));
    }

    @Step("Устанавливаем accessToken в localStorage")
    public void setAccessTokenToLocalStorage(String token) {
        ((JavascriptExecutor) driver).executeScript(
                String.format("window.localStorage.setItem('accessToken', '%s');", token)
        );
    }

    @Step("Сохраняем refreshToken в localStorage")
    public void setRefreshTokenToLocalStorage(String refreshToken) {
        ((JavascriptExecutor) driver).executeScript(
                String.format("localStorage.setItem('refreshToken','%s');", refreshToken)
        );
    }

    @Step("Кликаем на кнопку «Конструктор»")
    public void clickConstructorButton() {
        WebElementInteractions.clickElement(driver, ProfilePageLocators.CONSTRUCTOR_BUTTON);
    }

    @Step("Кликаем на логотип Stellar Burgers")
    public void clickLogo() {
        WebElementInteractions.clickElement(driver, ProfilePageLocators.LOGO_STELLAR_BURGERS);
    }

    @Step("Кликаем на кнопку «Выйти»")
    public void clickLogoutButton() {
        WebElementInteractions.clickElement(driver, ProfilePageLocators.LOGOUT_BUTTON);
    }
}