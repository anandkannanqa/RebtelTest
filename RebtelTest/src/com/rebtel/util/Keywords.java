

package com.rebtel.util;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Keywords {

	WebDriver driver = null;
	WebDriverWait wait = null;
	WebDriver bak_chrome;
	WebDriver bak_mozilla;
	WebDriver bak_ie;
	Properties OR=null;
	Properties ENV=null;
	String currentBrowser=null;
	static Keywords session;
	int mozillaCounter=0;
	int chromeCounter=0;
	int ieCounter=0;
	WebElement element = null;

	private static Logger Log = Logger.getLogger(Keywords.class.getName());
	private Keywords() {

		try{

			OR = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//rebtel//config//OR.properties");
			OR.load(fs);
			//init env
			ENV=new Properties();
			String fileName=OR.getProperty("environment")+".properties";
			Log.info("The test is executed from environment; "+fileName);
			// fileName is passed so that either prod.properties (production env) or uat.properties (staging env) can be selected
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//rebtel//config//"+fileName);
			ENV.load(fs);

		}catch(Exception e){

			Log.info("the properties file cannot be read and the exception is " +e.getLocalizedMessage());

		}

	}

	/**
	 * Singleton instance of keywords class
	 * 
	 */
	public static Keywords getInstance(){

		if(session==null)
			session=new Keywords();
		return session;

	}

	/**
	 *Initializes a new browser instance
	 * @param browserName
	 * @throws MalformedURLException 
	 * @throws InterruptedException 
	 * 
	 */
	public void openBrowser(String browserName) throws MalformedURLException, InterruptedException{


		if(browserName.equals(currentBrowser))
			return;

		if(browserName.equals("Mozilla"))	{	
			//	DesiredCapabilities capability = DesiredCapabilities.firefox();
			if(mozillaCounter==0)
				driver = new FirefoxDriver();
			//	driver = new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), capability); //this can be used for parallel execution in a server or a grid setup
			wait = new WebDriverWait(driver, 15);
			currentBrowser="Mozilla";
			mozillaCounter=1;


		}

		else if(browserName.equals("Chrome")){
			Thread.sleep(5000L);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//com//rebtel//drivers//chromedriver.exe");
			//	DesiredCapabilities capability = DesiredCapabilities.chrome();
			if(chromeCounter==0)
				driver = new ChromeDriver();
			//	driver = new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), capability); //this can be used for parallel execution in a server or a grid setup
			wait = new WebDriverWait(driver, 15);
			currentBrowser="Chrome";
			chromeCounter=1;

		}

		else if(browserName.equals("IE")){
			Thread.sleep(5000L);
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//src//com//rebtel//drivers//IEDriverServer.exe");
			if(ieCounter==0)
				driver = new InternetExplorerDriver();
			wait = new WebDriverWait(driver, 15);
			currentBrowser="IE";
			ieCounter=1;

		}

		// Used to expland the window
		driver.manage().window().maximize();

		//Using implicit waits we poll the webpage every second till the elements become visible and it expires after maximum of ten seconds in this case
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );

	}


	/**
	 * Initializes a new browser instance when set to different in configuration
	 * @param browserInstance
	 * 
	 */
	public void counter(String browserInstance) {

		if((OR.getProperty(browserInstance).equals("different"))){
			mozillaCounter=0;
			chromeCounter=0;
			ieCounter=0;
		}

	}


	/**
	 * Returns true if webpage title of SUT matches otherwise false
	 * @param expectedTitleKey
	 * @throws InterruptedException 
	 * 
	 */
	public boolean validateTitle(String expectedTitleKey) throws InterruptedException{

		String expectedTitle=OR.getProperty(expectedTitleKey);
		String actualTitle=driver.getTitle();
		wait.until(ExpectedConditions.titleContains(actualTitle));
		if(expectedTitle.equals(actualTitle))
			return true;
		else
			return false;
	}


	/**
	 * Loading the test URL 
	 * @param URL
	 * 
	 */
	public void navigate(String URL) {
		// TODO Auto-generated method stub
		driver.get(ENV.getProperty(URL));
		Log.info("Navigating to the URL"+ENV.getProperty(URL));
	}



	/**
	 * Simulate a input action eg: entering a username or password in the textbox of login form
	 * @param xpath
	 * @param text
	 * 
	 */
	public void input(String xpath, String text) {

		try{
			driver.findElement(By.xpath(OR.getProperty(xpath))).sendKeys(text);
		}catch(Throwable t){
			// report error
			Log.info("Error while wrinting into input - " + t.getMessage());
		}

	}


	/**
	 * Simulate a Click action on the given element 
	 * @param xpath
	 * 
	 */
	public void click(String xpath){
		try{
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS );
			driver.findElement(By.xpath(OR.getProperty(xpath))).click();
		}catch(Throwable t){
			// report error
			Log.info("Error while clicking the element - " + xpath + t.getMessage());
		}

	}


	/**
	 * Simulate a UK mobile number (the country was specified by Claes) 
	 * @param xpath
	 * 
	 */
	public String generateMobileNumber() {

		int min=333333333;
		int maxi=999999999;
		int diff=maxi-min;
		Random rn = new Random();
		int num = rn.nextInt(diff+1);
		num+=min;
		String  number = Integer.toString(num);
		number = "+44"+number;
		return number;

	}

	/**
	 * Simulate a German mobile number (the country was specified by Claes) 
	 * @param xpath
	 * 
	 */

	public boolean checkIfacccountExistsAlready(String xpath) {
		String errorMessage = driver.findElement(By.xpath(OR.getProperty(xpath))).getText();
		Log.info(errorMessage);
		if(errorMessage.contains("User already exists: ")){
			return true;
		}

		return false;

	}

	public boolean pageHasLoaded(String xpath) {
		// TODO Auto-generated method stub
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS );
		return  driver.findElement(By.xpath(OR.getProperty(xpath))).isDisplayed();

	}

	public void closeBroser() {

		if (driver != null)
			driver.quit();
	}


}






