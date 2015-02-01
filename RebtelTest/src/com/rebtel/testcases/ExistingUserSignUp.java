package com.rebtel.testcases;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rebtel.util.Keywords;

public class ExistingUserSignUp {
	Keywords session = Keywords.getInstance();

	@Test(dataProvider="getData")
	public void doSignupNewNumber(String email, String password, String browserName, String flag, String userNumber) throws InterruptedException, MalformedURLException     { 


		System.out.println("Executing Sign Test with userNumber : " +userNumber + " & password: "+password );
		if(flag.equals("N")){
			throw new SkipException("Skipping as Flag is N");
		}
		session.openBrowser(browserName);
		session.counter("browserInstance");
		session.navigate("testSiteURL");
		Assert.assertTrue(session.validateTitle("homePageTitle"), "Home page titles did not match");
		session.click("singUpNow_button");
		session.input("enterEmail", userNumber); // Helps in generating random email address
		session.input("phoneNumber", userNumber);
		session.input("password", password);
		session.click("agreeTermsofService");
		session.click("createNewAccount");
	}


	@DataProvider
	public Object[][] getData() {

		Object data[][] = new Object[3][5];

		data[0][0] = "test@qalabs.se";
		data[0][1] = "1234";
		data[0][2] = "Mozilla";
		data[0][3] = "Y";
		data[0][4] = "+46761908202";

		data[1][0] = "anand@qalabs.se";
		data[1][1] = "4321";
		data[1][2] = "Chrome";
		data[1][3] = "Y";
		data[0][4] = "+46761908202";


		data[2][0] = "rebtel@qalabs.se";
		data[2][1] = "3454";
		data[2][2] = "Mozilla";
		data[2][3] = "Y";
		data[0][4] = "+46761908202";
		return data;


	}

	@AfterSuite
	public void CloseBrowser(){

		session.closeBroser();

	}
}



