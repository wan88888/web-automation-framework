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
import org.slf4j.Logger;
import utils.LoggerUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    private static final Logger logger = LoggerUtil.getLogger(Hooks.class);
    
    @BeforeAll
    public static void setupAll() {
        LoggerUtil.logTestSuite(logger, "Starting test suite execution");
        DriverFactory.initDriver();
        DriverFactory.getDriver().get(ConfigReader.getBaseUrl());
        LoggerUtil.logBrowserAction(logger, "Browser initialized and navigated to: " + ConfigReader.getBaseUrl());
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        LoggerUtil.logScenarioStart(logger, scenario.getName());
        // Clear browser state before each scenario
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().deleteAllCookies();
        driver.navigate().to(ConfigReader.getBaseUrl());
        LoggerUtil.logBrowserAction(logger, "Cleared cookies and navigated to base URL");
    }

    @After
    public void afterScenario(Scenario scenario) {
        String status = scenario.getStatus().toString();
        LoggerUtil.logScenarioEnd(logger, scenario.getName(), status);
        
        // Take screenshot if scenario failed and configuration allows
        if (scenario.isFailed() && ConfigReader.isTakeScreenshotOnFailure()) {
            logger.info("Scenario failed, capturing screenshot");
            takeScreenshot(scenario);
        }
    }

    @AfterAll
    public static void tearDownAll() {
        LoggerUtil.logTestSuite(logger, "Closing browser and ending test suite");
        DriverFactory.quitDriver();
        LoggerUtil.logTestSuite(logger, "Test suite execution completed");
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
                LoggerUtil.logScreenshot(logger, "Screenshot attached to scenario: " + scenario.getName());
            }
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Failed to take screenshot for scenario: " + scenario.getName(), e);
        }
    }

    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
