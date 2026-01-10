package utils;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

public class AssertUtils {

    public static void assertEquals(String actual, String expected, String passMessage, String failMessage) {
        ExtentTest test = TestNGListener.extentTest.get();

        try {
            Assert.assertEquals(actual, expected);
            SmartLogger.pass("Assertion Passed ✅: " + passMessage);
        } catch (AssertionError e) {
            SmartLogger.fail("Assertion Failed ❌: " + passMessage);
            throw e;
        }
    }
    
    public static void assertTrue(boolean actual, String passMessage, String failMessage) {
        ExtentTest test = TestNGListener.extentTest.get();

        try {
            Assert.assertTrue(actual);
            SmartLogger.pass("Assertion Passed ✅: " + passMessage);
        } catch (AssertionError e) {
            SmartLogger.fail("Assertion Failed ❌: " + passMessage);
            throw e;
        }
    }
}

