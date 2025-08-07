package hooks;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import core.DriverFactory;
import core.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    
    @BeforeAll
    public static void setupAll() {
        System.out.println("[" + getCurrentTimestamp() + "] Starting test suite execution");
        DriverFactory.initDriver();
        DriverFactory.getDriver().get(ConfigReader.getBaseUrl());
        System.out.println("[" + getCurrentTimestamp() + "] Browser initialized and navigated to: " + ConfigReader.getBaseUrl());
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("[" + getCurrentTimestamp() + "] Starting scenario: " + scenario.getName());
        // Clear browser state before each scenario
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().deleteAllCookies();
        driver.navigate().to(ConfigReader.getBaseUrl());
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("[" + getCurrentTimestamp() + "] Finished scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());
        
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            System.out.println("[" + getCurrentTimestamp() + "] Scenario failed, taking screenshot");
            takeScreenshot(scenario);
        }
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("[" + getCurrentTimestamp() + "] Closing browser and ending test suite");
        DriverFactory.quitDriver();
        System.out.println("[" + getCurrentTimestamp() + "] Test suite execution completed");
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
        } catch (Exception e) {
            System.err.println("[" + getCurrentTimestamp() + "] Failed to take screenshot: " + e.getMessage());
        }
    }

    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
