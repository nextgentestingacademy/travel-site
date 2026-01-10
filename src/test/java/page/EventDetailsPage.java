package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;
import utils.SmartLogger;

public class EventDetailsPage extends BasePage {
	private static String rating;
	// Page Factory element

	@FindBy(css = "[class^='MovieDetailWidget_largeHeading']")
	WebElement lblMovieTitle;

	@FindBy(css = "[class^='MovieDetailWidget_subHeading']")
	WebElement lblMovieSubHeading;

	@FindBy(xpath = "//span[@data-testid='title']")
	WebElement lblLanguagePopUpMovie;

	@FindBy(xpath = "//span[text()='Choose preferred language']")
	WebElement lblChooseLanguage;

	@FindBy(xpath = "//label[@for='Hindi_lsd']")
	WebElement rdHindi;

	@FindBy(xpath = "//label[@for='English_lsd']")
	WebElement rdEnglish;

	@FindBy(xpath = "//button[@aria-label='Proceed']")
	WebElement btnProceed;

	@FindBy(xpath = "//i[@data-testid='close-icon']")
	WebElement btnClose;

	@FindBy(xpath = "//li[@aria-label='filter']")
	WebElement btnFilter;
	
	@FindBy(xpath = "//div[contains(text(),'Show Time')]")
	WebElement btnShowTime;

	@FindBy(xpath = "//div[contains(text(),'Price')]")
	WebElement btnPriceRange;
	
	@FindBy(xpath = "//div[contains(text(),'Others')]")
	WebElement btnOthers;

	@FindBy(xpath = "//button[contains(@aria-label,'View')]")
	WebElement btnView;
	
	@FindBy(xpath = "//button[contains(@aria-label,'No show')]")
	WebElement btnNoShowsAvailable;
	
	@FindBy(xpath = "//span[text()='Clear filter']")
	WebElement btnClearFilter;
	
	@FindBy(xpath = "//i[@data-testid='close-icon']")
	WebElement btnCloseFilter;
	
	@FindBy(xpath = "//li[contains(@class,'MovieSessionsListing_movieSessions')]")
	List<WebElement> lstMovieListingDetails;

	@FindBy(xpath = "(//span[@class='MovieDetailWidget_pipeSeperatedItems__wrcrr'])[1]")
	WebElement lblMovieRating;
	
	@FindBy(xpath = "//button[text()='Confirm and proceed']")
	WebElement btnConfirmProceed;

	// constructor
	public EventDetailsPage() {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	// handle dynamic web elements
	public WebElement selectDay(int day) throws InterruptedException {
//		Thread.sleep(2000);
		String xpath = "(//a[contains(@class,'DatesMobileV2_cinemaDates_')])[" + day + "]";
		return DriverFactory.getDriver().findElement(By.xpath(xpath));
	}
	
	public WebElement selectFilter(int filter) throws InterruptedException {
//		Thread.sleep(2000);
		String xpath = "(//span[@class='CheckBox_check__yOp7Y'])[" + filter + "]";
		return DriverFactory.getDriver().findElement(By.xpath(xpath));
	}

	// page specific methods
	public EventDetailsPage handleLanguageSelectIfPresent(String language) {
		if (isPresent(lblChooseLanguage)) {
			SmartLogger.info("Language selection pop up is displayed");
			switch (language) {
			case "English":
				click(rdEnglish);
				SmartLogger.info("Selected language as English");
				break;
			case "Hindi":
				click(rdHindi);
				SmartLogger.info("Selected language as Hindi");
				break;
			}
			click(btnProceed);
			SmartLogger.info("Clicked on Proceed button");
		}
		return this;
	}

	public EventDetailsPage addFilter(int day, String format, String showTime, String priceRange, String others) throws InterruptedException {
		rating = checkRating();
		
		click(selectDay(day+1));
		if(day==0) {
			SmartLogger.info("Selected movie day as today");
		}else {
			SmartLogger.info("Selected movie day as " + day + " day from today");
		}
		if(format!="") {
			click(btnFilter);
			SmartLogger.info("Clicked on Filter button");
			switch(format) {
				case "3D":
					click(selectFilter(1));
					SmartLogger.info("Selected format as 3D");
					break;
				case "IMAX 3D":
					click(selectFilter(2));
					SmartLogger.info("Selected format as IMAX 3D");
					break;
				default:
			}
		}
		if(showTime!="") {
			click(btnShowTime);
			switch(showTime) {
				case "Early Morning":
					click(selectFilter(1));
					SmartLogger.info("Selected show time as Early Morning");
					break;
				case "Morning":
					click(selectFilter(2));
					SmartLogger.info("Selected show time as Morning");
					break;
				case "Afternoon":
					click(selectFilter(3));
					SmartLogger.info("Selected show time as Afternoon");
					break;
				case "Evening":
					click(selectFilter(4));
					SmartLogger.info("Selected show time as Evening");
					break;
				case "Night":
					click(selectFilter(5));
					SmartLogger.info("Selected show time as Night");
					break;
				default:
			}
		}
		if(priceRange!="") {
			click(btnPriceRange);
			switch(priceRange) {
				case "200-300":
					click(selectFilter(1));
					SmartLogger.info("Selected price range as 200-300");
					break;
				case "300-400":
					click(selectFilter(2));
					SmartLogger.info("Selected price range as 300-400");
					break;
				case "400-500":
					click(selectFilter(3));
					SmartLogger.info("Selected price range as 400-500");
					break;
				default:
			}
		}
		if(others!="") {
			click(btnOthers);
			switch(others) {
				case "Wheelchair Friendly":
					click(selectFilter(1));
					SmartLogger.info("Selected Wheelchair Friendly");
					break;
				case "Recliners":
					click(selectFilter(2));
					SmartLogger.info("Selected Recliners");
					break;
				case "Premium Seats":
					click(selectFilter(3));
					SmartLogger.info("Selected Premium Seats");
					break;
				default:
			}
		}
		click(btnView);
		SmartLogger.info("Clicked on View button");
		return this;
	}

	public EventDetailsPage selectTheatreAndShow(String theatre, String time) throws InterruptedException {
		Thread.sleep(2000);
		if(theatre!="") {
			for(WebElement movieList: lstMovieListingDetails) {
				String theatreName=movieList.findElement(By.xpath(".//div[contains(@class,'MovieSessionsListing_titleFlex__mE_KX')]/a")).getText();
				if(theatreName.trim().toLowerCase().contains(theatre.trim().toLowerCase())) {
					List<WebElement> lstShowTime=movieList.findElements(By.xpath(".//div[contains(@class,'MovieSessionsListing_time___f5tm')]"));
					for(WebElement showTime: lstShowTime) {
						String show=showTime.getText();
						if(show.trim().toLowerCase().contains(time.trim().toLowerCase())) {
							showTime.click();
							SmartLogger.info("Selected theatre as " + theatre + " and time as " + time);
							return this;
						}
					}
				}
			}
		}else {
			lstMovieListingDetails.get(0).findElement(By.xpath("(//div[contains(@class,'MovieSessionsListing_time___f5tm')])[1]")).click();
			SmartLogger.info("Selected whichever theatre and time was available");
		}
		return this;
	}
	
	public String validateMovieTitle(){
		String movieTitle = lblMovieTitle.getText();
		SmartLogger.info("Movie Title is displayed as " + movieTitle);
		return movieTitle;
	}

	public String checkRating() {
		String rating = lblMovieRating.getText();
		SmartLogger.info("Movie Rating is displayed as " + rating);
		return rating;
	}

	public SeatLayoutPage handleARatingIfPresent() {
		if(rating.equalsIgnoreCase("A")) {
			if(isPresent(btnConfirmProceed)) {
				safeClick(btnConfirmProceed);
				SmartLogger.info("Clicked on Confirm And Proceed button");
			}
		}
		return new SeatLayoutPage();
	}
}