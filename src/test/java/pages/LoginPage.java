package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import utils.LoggerUtil;

public class LoginPage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(LoginPage.class);
    
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.debug("LoginPage initialized");
    }

    public void enterUsername(String username) {
        LoggerUtil.logElementAction(logger, "Entering username", usernameInput.toString());
        highlightElement(usernameInput);
        clearAndType(usernameInput, username);
        logger.debug("Username entered successfully");
    }

    public void enterPassword(String password) {
        LoggerUtil.logElementAction(logger, "Entering password", passwordInput.toString());
        highlightElement(passwordInput);
        clearAndType(passwordInput, password);
        logger.debug("Password entered successfully");
    }

    public void clickLogin() {
        LoggerUtil.logElementAction(logger, "Clicking login button", loginButton.toString());
        highlightElement(loginButton);
        click(loginButton);
        logger.debug("Login button clicked successfully");
    }

    public String getCurrentUrl() {
        String url = super.getCurrentUrl();
        logger.debug("Current URL retrieved: {}", url);
        return url;
    }
    
    public boolean isUsernameFieldVisible() {
        return isElementVisible(usernameInput);
    }
    
    public boolean isPasswordFieldVisible() {
        return isElementVisible(passwordInput);
    }
    
    public boolean isLoginButtonVisible() {
        return isElementVisible(loginButton);
    }
    
    public boolean isLoginButtonClickable() {
        return isElementClickable(loginButton);
    }
    
    public boolean isErrorMessageDisplayed() {
        LoggerUtil.logElementAction(logger, "Checking if error message is displayed", errorMessage.toString());
        boolean isDisplayed = isElementVisible(errorMessage);
        logger.debug("Error message visibility: {}", isDisplayed);
        return isDisplayed;
    }
    
    public String getErrorMessage() {
        LoggerUtil.logElementAction(logger, "Getting error message text", errorMessage.toString());
        String message = getElementText(errorMessage);
        logger.debug("Error message text: {}", message);
        return message;
    }
}