package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.Dimension;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import utils.LoggerUtil;

import java.time.Duration;

public class DriverFactory {
    private static final Logger logger = LoggerUtil.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void initDriver() {
        if (tlDriver.get() == null) {
            String browser = ConfigReader.getBrowser().toLowerCase();
            LoggerUtil.logConfig(logger, "Browser", browser);
            LoggerUtil.logConfig(logger, "Headless Mode", String.valueOf(ConfigReader.isHeadlessMode()));
            LoggerUtil.logConfig(logger, "Window Size", ConfigReader.getWindowSize());
            
            WebDriver driver = createDriver(browser);
            configureDriver(driver);
            tlDriver.set(driver);
            logger.info("WebDriver initialized successfully for browser: {}", browser);
        }
    }

    private static WebDriver createDriver(String browser) {
        WebDriver driver;
        boolean headless = ConfigReader.isHeadlessMode();

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "safari":
                // Safari doesn't support headless mode
                if (headless) {
                    LoggerUtil.logWarning(logger, "Safari doesn't support headless mode. Running in normal mode.");
                }
                driver = new SafariDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        return driver;
    }

    private static void configureDriver(WebDriver driver) {
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigReader.getScriptTimeout()));

        // Set window size
        String windowSize = ConfigReader.getWindowSize();
        switch (windowSize.toLowerCase()) {
            case "maximize":
                driver.manage().window().maximize();
                break;
            case "fullscreen":
                driver.manage().window().fullscreen();
                break;
            default:
                // Handle specific dimensions like "1920x1080"
                if (windowSize.contains("x")) {
                    String[] dimensions = windowSize.split("x");
                    try {
                        int width = Integer.parseInt(dimensions[0]);
                        int height = Integer.parseInt(dimensions[1]);
                        driver.manage().window().setSize(new Dimension(width, height));
                    } catch (NumberFormatException e) {
                        LoggerUtil.logWarning(logger, "Invalid window size format: " + windowSize + ". Using maximize instead.");
                        driver.manage().window().maximize();
                    }
                } else {
                    driver.manage().window().maximize();
                }
                break;
        }
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}