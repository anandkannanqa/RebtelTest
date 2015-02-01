package com.rebtel.config;

import org.testng.annotations.DataProvider;

public class Data_Provider {

	@DataProvider(name="SignUpTest")
	public static Object[][] getSignUpData() {

		Object data[][] = new Object[3][3];

		data[0][0] = "test@qalabs.se";
		data[0][1] = "1234";
		data[0][2] = "Y";

		data[1][0] = "anand@qalabs.se";
		data[1][1] = "4321";
		data[1][2] = "Y";

		data[2][0] = "rebtel@qalabs.se";
		data[2][1] = "3454";
		data[2][2] = "Y";
		return data;

	}

	@DataProvider(name="SignUpPage")
	public static Object[][] getSignUpPageData() {
		Object data[][] = new Object[3][1];

		data[0][0] = "Mozilla";

		data[1][0] = "Chrome";

		data[2][0] = "Mozilla";
		return data;

	} 
}