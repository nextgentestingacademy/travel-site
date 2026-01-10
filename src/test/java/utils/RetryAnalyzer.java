package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	private int retryCount = 0;
	private int maxRetry = Integer.parseInt(ConfigReader.get("retry"));
	
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount < maxRetry) {
			retryCount++;
			return true;
		}
		
		return false;
	}
}