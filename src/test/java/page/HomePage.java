package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;
import utils.SmartLogger;

public class HomePage extends BasePage {
	// Page Factory element
	@FindBy(css=".dds-text-primary.dds-text-lg.dds-font-semibold.dds-overflow-hidden.dds-whitespace-normal.dds-line-clamp-1.dds-leading-relaxed")
	WebElement imgLocation;
	
	@FindBy(xpath="//input[contains(@placeholder,'Search city')]")
	WebElement edtSearchLocation;
	
	@FindBy(xpath="(//a[@class='dds-w-full'])[1]")
	WebElement lnkSearch;
	
	@FindBy(xpath="(//input[contains(@class,'dds-rounded')])[2]")
	WebElement edtSearchActivity;
	
	// constructor
	public HomePage() {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	// handle dynamic web elements
	public WebElement getCityOption(String city) throws InterruptedException {
		Thread.sleep(2000);
		String xpath = "//span[normalize-space()='" + city + "']";
		return DriverFactory.getDriver().findElement(By.xpath(xpath));
	}
	
	public WebElement getActivity(String activity) {
		String xpath = "//span[normalize-space()='" + activity + "']";
		return DriverFactory.getDriver().findElement(By.xpath(xpath));
	}
	
	public WebElement getMovieName(String movie) {
		String xpath = "(//h5[normalize-space()='" + movie + "'])[2]";
		return DriverFactory.getDriver().findElement(By.xpath(xpath));
	}

	// page specific methods
	public void selectLocation(String city) throws InterruptedException {
		Thread.sleep(5000);
		click(imgLocation);
		SmartLogger.info("Clicked on Search location");
		clickUsingActions(edtSearchLocation);
    	humanTypeUsingActions(edtSearchLocation, city);
    	SmartLogger.info("Entered " + city);
        click(getCityOption(city));
        SmartLogger.info("Clicked on " + city);
//        return this;
	}
	
	public void selectActivity(String activity) {
		click(lnkSearch);
		SmartLogger.info("Clicked on Search icon");
		click(getActivity(activity));
		SmartLogger.info("Clicked on " + activity);
//        return this;
    }

    public void searchMovie(String movieName) throws InterruptedException {
    	clickUsingActions(edtSearchActivity);
    	humanTypeUsingActions(edtSearchActivity, movieName);
    	SmartLogger.info("Entered " + movieName);
    	Thread.sleep(2000);
    	safeClick(getMovieName(movieName));
    	SmartLogger.info("Clicked on " + movieName);
//        return new MovieDetailsPage();
    }
    
    public void searchEvent(String eventName) throws InterruptedException {
    	clickUsingActions(edtSearchActivity);
    	humanTypeUsingActions(edtSearchActivity, eventName);
    	SmartLogger.info("Entered " + eventName);
    	Thread.sleep(2000);
    	safeClick(getMovieName(eventName));
    	SmartLogger.info("Clicked on " + eventName);
//        return new EventDetailsPage();
    }
}