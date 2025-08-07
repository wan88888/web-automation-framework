package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        waitForElementToBeVisible(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        waitForElementToBeVisible(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        waitForElementToBeClickable(loginButton).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}