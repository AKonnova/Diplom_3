package util;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebElementInteractions {

    private static final By MODAL_OVERLAY_LOCATOR = By.className("Modal_modal_overlay__x2ZCr");
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    @Step("Клик по элементу с ожиданием исчезновения оверлея")
    public static void clickElement(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(MODAL_OVERLAY_LOCATOR));
        } catch (Exception e) {

        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Step("Клик по элементу по локатору с ожиданием исчезновения оверлея")
    public static void clickElement(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        clickElement(driver, element);
    }
}