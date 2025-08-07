package pages;

import core.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // Basic wait methods
    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> waitForElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected boolean waitForElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // Element existence and visibility checks
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementVisible(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Text operations
    protected String getElementText(By locator) {
        return waitForElementToBeVisible(locator).getText();
    }

    protected String getElementAttribute(By locator, String attributeName) {
        return waitForElementToBeVisible(locator).getAttribute(attributeName);
    }

    protected String getElementValue(By locator) {
        return getElementAttribute(locator, "value");
    }

    // Input operations
    protected void clearAndType(By locator, String text) {
        WebElement element = waitForElementToBeClickable(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void typeText(By locator, String text) {
        waitForElementToBeClickable(locator).sendKeys(text);
    }

    protected void pressKey(By locator, Keys key) {
        waitForElementToBeClickable(locator).sendKeys(key);
    }

    // Click operations
    protected void click(By locator) {
        waitForElementToBeClickable(locator).click();
    }

    protected void clickWithJS(By locator) {
        WebElement element = waitForElementToBePresent(locator);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    protected void doubleClick(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        actions.doubleClick(element).perform();
    }

    protected void rightClick(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        actions.contextClick(element).perform();
    }

    // Dropdown operations
    protected void selectByVisibleText(By locator, String text) {
        WebElement dropdown = waitForElementToBeClickable(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        WebElement dropdown = waitForElementToBeClickable(locator);
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        WebElement dropdown = waitForElementToBeClickable(locator);
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }

    protected String getSelectedOptionText(By locator) {
        WebElement dropdown = waitForElementToBeVisible(locator);
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    protected List<WebElement> getAllOptions(By locator) {
        WebElement dropdown = waitForElementToBeVisible(locator);
        Select select = new Select(dropdown);
        return select.getOptions();
    }

    // Scroll operations
    protected void scrollToElement(By locator) {
        WebElement element = waitForElementToBePresent(locator);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToTop() {
        jsExecutor.executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void scrollByPixels(int x, int y) {
        jsExecutor.executeScript("window.scrollBy(" + x + "," + y + ");");
    }

    // Mouse operations
    protected void hoverOverElement(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        actions.moveToElement(element).perform();
    }

    protected void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitForElementToBeVisible(sourceLocator);
        WebElement target = waitForElementToBeVisible(targetLocator);
        actions.dragAndDrop(source, target).perform();
    }

    // JavaScript operations
    protected Object executeJavaScript(String script, Object... args) {
        return jsExecutor.executeScript(script, args);
    }

    protected void highlightElement(By locator) {
        if (ConfigReader.isHighlightElements()) {
            WebElement element = waitForElementToBePresent(locator);
            String originalStyle = element.getAttribute("style");
            jsExecutor.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red; background-color: yellow;');", element);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            jsExecutor.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
        }
    }

    // Page operations
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void navigateBack() {
        driver.navigate().back();
    }

    protected void navigateForward() {
        driver.navigate().forward();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void switchToFrame(By frameLocator) {
        WebElement frame = waitForElementToBePresent(frameLocator);
        driver.switchTo().frame(frame);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // Wait utilities
    protected void waitForPageToLoad() {
        wait.until(webDriver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}