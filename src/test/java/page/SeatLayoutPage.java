package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;
import utils.SmartLogger;

public class SeatLayoutPage extends BasePage {
	// Page Factory element

	@FindBy(xpath = "//span[text()='Proceed']")
	WebElement btnProceed;

	@FindBy(name = "mobileNumber")
	WebElement edtMobileNumber;

	@FindBy(xpath = "//button[text()='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//span[@id='available-seat']")
	List<WebElement> lstAvailableSeat;

	// constructor
	public SeatLayoutPage() {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	// page specific methods
	public void selectSeatCount(int i) throws InterruptedException {
		Thread.sleep(2000);
		int count=0;
		for(WebElement seat: lstAvailableSeat) {
			if(count<i) {
				click(seat);
				count++;
			}else {
				break;
			}
		}
		SmartLogger.info("Selected " + i + " number of seats");
		click(btnProceed);
		SmartLogger.info("Clicked on Proceed button");
//		return this;
	}

	public boolean enterMobileNumber(String string) {
		type(edtMobileNumber,string);
		SmartLogger.info("Entered mobile number as " + string);
		click(btnContinue);
		SmartLogger.info("Clicked on Continue button");
		return true;
	}
}