package steps;

import core.DriverFactory;
import core.TestDataReader;
import pages.LoginPage;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.testng.Assert;
import utils.LoggerUtil;

public class LoginSteps {
    private static final Logger logger = LoggerUtil.getLogger(LoginSteps.class);
    private LoginPage loginPage;

    @Given("User is on SauceDemo login page")
    public void user_is_on_login_page() {
        LoggerUtil.logTestStep(logger, "Initializing login page");
        loginPage = new LoginPage(DriverFactory.getDriver());
        LoggerUtil.logTestStep(logger, "User is on SauceDemo login page");
    }

    @When("User enters valid username and password")
    public void user_enters_credentials() {
        LoggerUtil.logTestStep(logger, "Entering login credentials");
        String username = TestDataReader.getLoginUsername();
        String password = TestDataReader.getLoginPassword();
        
        LoggerUtil.logTestData(logger, "username", username);
        LoggerUtil.logTestData(logger, "password", password);
        
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        LoggerUtil.logTestStep(logger, "Successfully entered username and password");
    }

    @And("User clicks on the login button")
    public void user_clicks_login() {
        LoggerUtil.logTestStep(logger, "Clicking login button");
        loginPage.clickLogin();
        LoggerUtil.logTestStep(logger, "Login button clicked successfully");
    }

    @When("User enters invalid username and password")
    public void user_enters_invalid_credentials() {
        LoggerUtil.logTestStep(logger, "Entering invalid login credentials");
        String invalidUsername = "invalid_user";
        String invalidPassword = "invalid_password";
        
        LoggerUtil.logTestData(logger, "username", invalidUsername);
        LoggerUtil.logTestData(logger, "password", invalidPassword);
        
        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(invalidPassword);
        LoggerUtil.logTestStep(logger, "Successfully entered invalid username and password");
    }

    @Then("User should be redirected to the home page")
    public void user_redirected_home() {
        LoggerUtil.logTestStep(logger, "Verifying user redirection to home page");
        String currentUrl = loginPage.getCurrentUrl();
        logger.info("Current URL: {}", currentUrl);
        
        Assert.assertTrue(currentUrl.contains("inventory.html"), 
            "Expected URL to contain 'inventory.html' but was: " + currentUrl);
        LoggerUtil.logTestStep(logger, "Successfully verified redirection to home page");
    }

    @Then("User should see an error message")
    public void user_should_see_error_message() {
        LoggerUtil.logTestStep(logger, "Verifying error message is displayed");
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        
        Assert.assertTrue(isErrorDisplayed, "Expected error message to be displayed but it was not found");
        
        String errorMessage = loginPage.getErrorMessage();
        logger.info("Error message displayed: {}", errorMessage);
        LoggerUtil.logTestStep(logger, "Successfully verified error message is displayed");
    }
}
