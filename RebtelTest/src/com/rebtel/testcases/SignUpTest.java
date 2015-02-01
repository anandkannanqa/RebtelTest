package com.rebtel.testcases;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import com.rebtel.config.Data_Provider;
import com.rebtel.util.Keywords;


public class SignUpTest {

		Keywords session = Keywords.getInstance();
		private static Logger Log = Logger.getLogger(Keywords.class.getName());

		@Test(priority=1, dataProviderClass=Data_Provider.class, dataProvider="SignUpPage")
		public void goToSignUpPage(String browserName) throws InterruptedException, MalformedURLException     { 

		
		session.openBrowser(browserName);
		Log.debug("The browser type opened is" +browserName);
		session.counter("browserInstance");
		session.navigate("testSiteURL");
			Assert.assertTrue(session.validateTitle("homePageTitle"), "Home page titles did not match");
		}
		
		@Test(priority=2, dependsOnMethods={"goToSignUpPage"}, dataProviderClass=Data_Provider.class, dataProvider="SignUpTest")
		public void doSignupNewNumber(String email, String password, String flag) throws InterruptedException, MalformedURLException     { 

		Log.info("This test is dependent on the signUpPageTest");
        Log.info("Generating the new mobile number");
		String userNumber = session.generateMobileNumber();
		Log.info("Generating the new mobile number");
		if(flag.equals("N"))
				throw new SkipException("Skipping as Flag is N");
		session.click("singUpNow_button");
		session.input("enterEmail", userNumber+email); // Helps in generating random email address
		session.input("phoneNumber", userNumber);
		session.input("password", password);
		session.click("agreeTermsofService");
		session.click("createNewAccount");
		if(session.pageHasLoaded("successfulFirstTimeSignPage"))
			Assert.assertTrue(session.validateTitle("addCreditPage"), "Make your purchase to make cheap international calls - Rebtel");
	}
		

	
	@AfterSuite
	public void CloseBrowser(){

		session.closeBroser();

	}
}

