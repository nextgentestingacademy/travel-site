package utils;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartLogger {

    private static final Logger log = LogManager.getLogger(SmartLogger.class);

    private static ExtentTest getExtentTest() {
        return TestNGListener.extentTest.get();
    }

    public static void info(String message) {
        log.info(message);                // Log file
        getExtentTest().info(message);    // Extent report
    }

    public static void pass(String message) {
        log.info(message);
        getExtentTest().pass(message);
    }

    public static void fail(String message) {
        log.error(message);
        getExtentTest().fail(message);
    }

    public static void warn(String message) {
        log.warn(message);
        getExtentTest().warning(message);
    }
}
