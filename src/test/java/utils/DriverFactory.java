package utils;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

	//creating an instance of ThreadLocal for handling multiple parallel drivers
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public static void initDriver(String browser) {
		boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
		
		switch(browser.trim().toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions optChrome = new ChromeOptions();
			if(headless) {
				optChrome.addArguments("--headless=new");
			}
			driver.set(new ChromeDriver(optChrome));
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions optEdge = new EdgeOptions();
			if(headless) {
				optEdge.addArguments("--headless=new");
			}
			driver.set(new EdgeDriver(optEdge));
			break;
		default:
			WebDriverManager.chromedriver().setup();
			ChromeOptions optDefault = new ChromeOptions();
			if(headless) {
				optDefault.addArguments("--headless=new");
			}
			driver.set(new ChromeDriver(optDefault));
			break;
		}
	}
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	
	public static void quitDriver() {
		if(driver.get()!=null) {
			driver.get().quit();
			driver.remove();
		}
	}
}