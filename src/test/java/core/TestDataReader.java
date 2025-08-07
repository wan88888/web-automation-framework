package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataReader {
    private static final Properties testData = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/testdata.properties")) {
            testData.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load testdata.properties", e);
        }
    }

    public static String getLoginUsername() {
        return testData.getProperty("login.username");
    }

    public static String getLoginPassword() {
        return testData.getProperty("login.password");
    }

    public static String getExpectedLoginUrl() {
        return testData.getProperty("login.expected.url");
    }

    public static String getTestData(String key) {
        return testData.getProperty(key);
    }
}