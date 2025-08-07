package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    
    /**
     * Get logger instance for the calling class
     * @param clazz The class for which logger is needed
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Get logger instance for the calling class name
     * @param className The class name for which logger is needed
     * @return Logger instance
     */
    public static Logger getLogger(String className) {
        return LoggerFactory.getLogger(className);
    }
    
    /**
     * Log test step information
     * @param logger The logger instance
     * @param stepDescription Description of the test step
     */
    public static void logTestStep(Logger logger, String stepDescription) {
        logger.info("[TEST STEP] {}", stepDescription);
    }
    
    /**
     * Log test scenario start
     * @param logger The logger instance
     * @param scenarioName Name of the scenario
     */
    public static void logScenarioStart(Logger logger, String scenarioName) {
        logger.info("[SCENARIO START] {}", scenarioName);
        logger.info("=" .repeat(50));
    }
    
    /**
     * Log test scenario end
     * @param logger The logger instance
     * @param scenarioName Name of the scenario
     * @param status Status of the scenario (PASSED/FAILED)
     */
    public static void logScenarioEnd(Logger logger, String scenarioName, String status) {
        logger.info("=" .repeat(50));
        logger.info("[SCENARIO END] {} - Status: {}", scenarioName, status);
    }
    
    /**
     * Log browser action
     * @param logger The logger instance
     * @param action Description of the browser action
     */
    public static void logBrowserAction(Logger logger, String action) {
        logger.debug("[BROWSER ACTION] {}", action);
    }
    
    /**
     * Log element interaction
     * @param logger The logger instance
     * @param action Action performed on element
     * @param locator Element locator
     */
    public static void logElementAction(Logger logger, String action, String locator) {
        logger.debug("[ELEMENT ACTION] {} on element: {}", action, locator);
    }
    
    /**
     * Log configuration information
     * @param logger The logger instance
     * @param configKey Configuration key
     * @param configValue Configuration value
     */
    public static void logConfig(Logger logger, String configKey, String configValue) {
        logger.info("[CONFIG] {}: {}", configKey, configValue);
    }
    
    /**
     * Log error with exception
     * @param logger The logger instance
     * @param message Error message
     * @param throwable Exception object
     */
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error("[ERROR] {}", message, throwable);
    }
    
    /**
     * Log warning message
     * @param logger The logger instance
     * @param message Warning message
     */
    public static void logWarning(Logger logger, String message) {
        logger.warn("[WARNING] {}", message);
    }
    
    /**
     * Log test data information
     * @param logger The logger instance
     * @param dataKey Data key
     * @param dataValue Data value (will be masked if it contains 'password')
     */
    public static void logTestData(Logger logger, String dataKey, String dataValue) {
        String maskedValue = dataKey.toLowerCase().contains("password") ? "****" : dataValue;
        logger.info("[TEST DATA] {}: {}", dataKey, maskedValue);
    }
    
    /**
     * Log screenshot capture
     * @param logger The logger instance
     * @param screenshotPath Path to the screenshot
     */
    public static void logScreenshot(Logger logger, String screenshotPath) {
        logger.info("[SCREENSHOT] Captured screenshot: {}", screenshotPath);
    }
    
    /**
     * Log test suite information
     * @param logger The logger instance
     * @param message Suite message
     */
    public static void logTestSuite(Logger logger, String message) {
        logger.info("[TEST SUITE] {}", message);
    }
}