package test;

import org.testng.annotations.Test;

import base.BaseTest;
import page.EventDetailsPage;
import page.HomePage;
import page.MovieDetailsPage;
import page.SeatLayoutPage;
import utils.AssertUtils;
import utils.DataProviderUtils;
import utils.SmartLogger;

public class EndToEndTest extends BaseTest{
	
	@Test(dataProvider="movie",dataProviderClass=DataProviderUtils.class)
	public void bookMovieTicket(String loc,String act,String movie,String lang,int day,String format,String showTime,String price,String others,String theatre,String show,int seat,String mobile) throws InterruptedException {
		SmartLogger.info("Test Case: bookMovieTicket has started");
		 
		HomePage homePage = new HomePage();
		MovieDetailsPage moviePage = new MovieDetailsPage();
		SeatLayoutPage seatPage = new SeatLayoutPage();
		
		homePage.selectLocation(loc);
		homePage.selectActivity(act);
		homePage.searchMovie(movie);
		moviePage.handleLanguageSelectIfPresent(lang);
		AssertUtils.assertEquals(movie,moviePage.validateMovieTitle(),"Movie selected correctly as " + movie,"Movie not selected correctly");
		moviePage.addFilter(day,format,showTime,price,others);
		moviePage.selectTheatreAndShow(theatre, show);
		moviePage.handleARatingIfPresent();
		seatPage.selectSeatCount(seat);
        AssertUtils.assertTrue(seatPage.enterMobileNumber(mobile),"Movie ticket booked","Movie ticket not booked");
        SmartLogger.info("Test Case: bookMovieTicket has completed");
	}

//	@Test(dataProvider="event",dataProviderClass=DataProviderUtils.class)
//	public void bookEventTicket(String loc,String act,String event,String lang,int day,String format,String showTime,String price,String others,String theatre,String show,int seat,String mobile) throws InterruptedException {
//		SmartLogger.info("Test Case: bookEventTicket has started");
//		HomePage homePage = new HomePage();
//		EventDetailsPage eventPage = new EventDetailsPage();	
//		
//		homePage.selectLocation(loc);
//		homePage.selectActivity(act);
//		homePage.searchEvent(event);
//		
//		SmartLogger.info("Test Case: bookMovieTicket has completed");
//					.handleLanguageSelectIfPresent(lang);
//		AssertUtils.assertEquals(event,eventPage.validateMovieTitle(),"Movie selected correctly as " + movie,"Movie not selected correctly");
//        
//        SeatLayoutPage seatPage = 
//				new MovieDetailsPage()
//        			.addFilter(day,format,showTime,price,others)
//        	        .selectTheatreAndShow(theatre, show)
//        			.handleARatingIfPresent()
//        	
//        			.selectSeatCount(seat);
//        AssertUtils.assertTrue(seatPage.enterMobileNumber(mobile),"Movie ticket booked","Movie ticket not booked");
//        
//	}

}