package steps;

import core.DriverFactory;
import pages.LoginPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage;

    @Given("User is on SauceDemo login page")
    public void user_is_on_login_page() {
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @When("User enters valid username and password")
    public void user_enters_credentials() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
    }

    @And("User clicks on the login button")
    public void user_clicks_login() {
        loginPage.clickLogin();
    }

    @Then("User should be redirected to the home page")
    public void user_redirected_home() {
        Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory.html"));
    }
}
