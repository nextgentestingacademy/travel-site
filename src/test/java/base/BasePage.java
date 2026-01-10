package base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;
import utils.DriverFactory;

public class BasePage {
	protected Actions actions;
	protected WebDriverWait wait;
	int time;
	
	// ========== Wait methods ==========
	public BasePage() {
		WebDriver driver = DriverFactory.getDriver();
		if (driver == null) {
	        throw new IllegalStateException(
	            "WebDriver is NULL. Page object created before @BeforeMethod execution."
	        );
	    }
		time = Integer.parseInt(ConfigReader.get("timeout"));
		
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
	}

	public WebElement waitForVisible(WebElement elm) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(elm));
		} catch (Exception e) {
	        return null;
	    }
	}

	public WebElement waitForClickable(WebElement elm) {
		return wait.until(ExpectedConditions.elementToBeClickable(elm));
	}

	public boolean waitForInvisibility(WebElement elm) {
		return wait.until(ExpectedConditions.invisibilityOf(elm));
	}

	public void waitForText(WebElement elm, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(elm, text));
	}

	// ========== Action methods ==========
	public void click(WebElement elm) {
		if (isPresent(elm)) {
			waitForClickable(elm).click();
		}
	}

	public boolean type(WebElement elm, String text) {
		try {
			waitForVisible(elm).sendKeys(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void humanType(WebElement element, String text) {
	    for (char c : text.toCharArray()) {
	        element.sendKeys(String.valueOf(c));
	        try {
	            Thread.sleep(200); // human delay
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
	}

	public String getText(WebElement elm) {
		try {
			return waitForVisible(elm).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isDisplayed(WebElement elm) {
		try {
			return waitForVisible(elm).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEnabled(WebElement elm) {
		try {
			return waitForVisible(elm).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSelected(WebElement elm) {
		try {
			return waitForVisible(elm).isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	// -------------------------
	// PAGE NAVIGATION
	// -------------------------

	public void open(String url) {
		DriverFactory.getDriver().get(url);
	}

	public String getPageTitle() {
		return DriverFactory.getDriver().getTitle();
	}

	// -------------------------
	// DROPDOWN
	// -------------------------

	public boolean selectByVisibleText(WebElement elm, String text) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByVisibleText(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectByValue(WebElement elm, String value) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByValue(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectByIndex(WebElement elm, int index) {
		try {
			Select sel = new Select(waitForVisible(elm));
			sel.selectByIndex(index);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectFromAutoSuggest(WebElement inputBox, List<WebElement> suggestions, String textToType,
			String valueToSelect) throws InterruptedException {

		// Type in input box
		wait.until(ExpectedConditions.visibilityOf(inputBox));
		wait.until(ExpectedConditions.elementToBeClickable(inputBox));
		jsClick(inputBox);
		jsSetValue(inputBox,textToType);
//		System.out.println(type(inputBox,textToType));

		// Wait until suggestions are visible
		wait.until(ExpectedConditions.visibilityOfAllElements(suggestions));
		Thread.sleep(3000);
		for (WebElement option : suggestions) {
			try {
				if (option.getText().contains(valueToSelect)) {
					wait.until(ExpectedConditions.elementToBeClickable(option));
					jsClick(option);
					return true;
				}
			} catch (StaleElementReferenceException e) {
				return false;
				// PageFactory list refresh handled implicitly on next interaction
			}
		}

		return false;
	}

	// -------------------------
	// SCROLLING
	// -------------------------

	public void scrollTo(WebElement elm) {
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elm);
	}

	public void scrollBy(int x, int y) {
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
	}

	// -------------------------
	// JS ACTIONS
	// -------------------------

	public void jsClick(WebElement elm) {
		waitForVisible(elm);
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", elm);
	}

	public void jsSetValue(WebElement elm, String value) {
		waitForVisible(elm);
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].value='" + value + "'", elm);
	}
	
	public void jsSetValueCharByChar(WebElement elm, String value) {
	    waitForVisible(elm);

	    JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();

	    // Clear existing value
	    js.executeScript("arguments[0].value='';", elm);

	    for (char ch : value.toCharArray()) {
	        js.executeScript(
	            "arguments[0].value = arguments[0].value + arguments[1];" +
	            "arguments[0].dispatchEvent(new Event('input'));" +
	            "arguments[0].dispatchEvent(new Event('change'));",
	            elm,
	            String.valueOf(ch)
	        );
	    }
	}


	public String jsGetText(WebElement elm) {
		waitForVisible(elm);
		return (String) ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("return arguments[0].textContent;", elm);
	}

	// -------------------------
	// ACTIONS CLASS UTILITIES
	// -------------------------

	public void clickUsingActions(WebElement elm) {
		actions.click(waitForVisible(elm)).perform();
	}
	
	public void hover(WebElement elm) {
		actions.moveToElement(waitForVisible(elm)).perform();
	}

	public void rightClick(WebElement elm) {
		actions.contextClick(waitForVisible(elm)).perform();
	}

	public void doubleClick(WebElement elm) {
		actions.doubleClick(waitForVisible(elm)).perform();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(waitForVisible(source), waitForVisible(target)).perform();
	}

	public void dragAndDropBy(WebElement elm, int x, int y) {
		actions.dragAndDropBy(waitForVisible(elm), x, y).perform();
	}

	public void typeUsingActions(WebElement elm, String text) {
		actions.moveToElement(waitForVisible(elm)).click().sendKeys(text).perform();
	}
	
	public void clickUsingOffset(int x,int y) {
		actions.moveByOffset(x, y).click().perform();
	}

	public void humanTypeUsingActions(WebElement element, String text) {
		for (char c : text.toCharArray()) {
		    actions.sendKeys(String.valueOf(c)).pause(Duration.ofMillis(120)).perform();
		}
	}
	
	// -------------------------
	// ALERTS
	// -------------------------

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	public void sendKeysToAlert(String text) {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(text);
		alert.accept();
	}

	// -------------------------
	// FRAME & WINDOW HANDLING
	// -------------------------

	public void switchToFrame(WebElement frameElement) {
		waitForVisible(frameElement);
		DriverFactory.getDriver().switchTo().frame(frameElement);
	}

	public void switchToFrameByIndex(int index) {
		DriverFactory.getDriver().switchTo().frame(index);
	}

	public void switchToDefault() {
		DriverFactory.getDriver().switchTo().defaultContent();
	}

	public void switchToWindow(String handle) {
		DriverFactory.getDriver().switchTo().window(handle);
	}

	// -------------------------
	// WAIT FOR PAGE LOAD
	// -------------------------

	public void waitForPageLoad() {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15)).until(webDriver -> ((JavascriptExecutor) DriverFactory.getDriver())
				.executeScript("return document.readyState").equals("complete"));
	}

	// -------------------------
	// RETRY CLICK (robust)
	// -------------------------

	public void safeClick(WebElement elm) {
		int attempts = 0;
		while (attempts < 3) {
			try {
				click(elm);
				return;
			} catch (Exception e) {
				attempts++;
			}
		}
	}

	// -------------------------
	// EXISTENCE CHECKS
	// -------------------------

	public boolean isPresent(WebElement elm) {
		try {
			if(waitForVisible(elm)!=null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	// -------------------------
	// DISPLAYED CHECKS
	// -------------------------

	public WebElement isDisplayed(List<WebElement> lstElem) {
	return lstElem.stream()
	        .filter(WebElement::isDisplayed)
	        .findFirst()
	        .orElseThrow(() -> new RuntimeException("Element not found"));
	}
	
	// -------------------------
	// CUSTOM WAIT FOR TEXT CHANGE
	// -------------------------

	public void waitForTextChange(WebElement elm, String expectedText) {
		wait.until(driver -> elm.getText().contains(expectedText));
	}
}