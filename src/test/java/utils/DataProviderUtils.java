package utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {
	
	@DataProvider(name="movie")
	public Object[][] bookMovie(){
		return new Object[][] {
			{"Mumbai","Movies","Avatar: Fire and Ash","English",0,"3D","Night","400-500","","PVR Dynamix, Juhu, Mumbai", "10:45 PM",2,"1234567890"},
			{"Mumbai","Movies","Dhurandhar","Hindi",1,"IMAX 2D","Night","","","", "",10,"9876543210"}
		};
	}
}