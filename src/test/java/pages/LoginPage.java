package pages;

import org.openqa.selenium.*;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        highlightElement(usernameInput);
        slowDown();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        highlightElement(passwordInput);
        slowDown();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        highlightElement(loginButton);
        slowDown();
        driver.findElement(loginButton).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}