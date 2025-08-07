package hooks;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import core.DriverFactory;
import core.ConfigReader;

public class Hooks {
    @BeforeAll
    public static void setupAll() {
        DriverFactory.initDriver();
        String url = ConfigReader.getBaseUrl();
        DriverFactory.getDriver().get(url);
    }

    @AfterAll
    public static void tearDownAll() {
        DriverFactory.quitDriver();
    }
}
