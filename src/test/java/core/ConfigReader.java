package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return prop.getProperty("baseUrl");
    }

    public static String getBrowser() {
        return prop.getProperty("browser", "chrome");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(prop.getProperty("explicitWait", "10"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(prop.getProperty("implicitWait", "5"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(prop.getProperty("pageLoadTimeout", "30"));
    }

    public static int getScriptTimeout() {
        return Integer.parseInt(prop.getProperty("scriptTimeout", "30"));
    }

    public static String getWindowSize() {
        return prop.getProperty("windowSize", "maximize");
    }

    public static String getEnvironment() {
        return prop.getProperty("environment", "test");
    }

    public static boolean isTakeScreenshotOnFailure() {
        return Boolean.parseBoolean(prop.getProperty("takeScreenshotOnFailure", "true"));
    }

    public static boolean isHighlightElements() {
        return Boolean.parseBoolean(prop.getProperty("highlightElements", "false"));
    }

    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(prop.getProperty("headlessMode", "false"));
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}