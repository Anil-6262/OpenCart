package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; // Log4j
import org.apache.logging.log4j.Logger;  // Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass
{

	public static WebDriver driver;
	public Logger logger;   // Log4j
	public Properties prop;

	@BeforeClass(groups = {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException
	{

		System.setProperty("selenium-manager-tracing", "false");


		// Loading properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);


		logger = LogManager.getLogger(this.getClass());  // Log4j2



		// Remote Machine

		if(prop.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();

			//OS
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching OS");
				return;
			}

			// Browser
			switch(br.toLowerCase())
			{
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("edge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:

				System.out.println("No matching browser");
				return;
			}
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);

		}



		// Local Machine
		if(prop.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" :  driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name..."); return;
			}
		}



		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("appURL2"));  // Reading url form properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups = {"Sanity","Regression","Master"})
	public void tearDown()
	{
		if(driver!=null)
		{
			driver.quit();
		}
	}


	public String captureScreenshot(String tName)
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot  takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+ tName + "_" + timeStamp + ".png";
		File targateFile = new File(targetFilePath);

		sourceFile.renameTo(targateFile);

		return targetFilePath;

	}




	// Generate Data at run time for multiple attempt

	public String randomString()
	{
		String generateString = RandomStringUtils.randomAlphabetic(5);
		return generateString;
	}

	public String randomNumber()
	{
		String generateNumber = RandomStringUtils.randomNumeric(10);
		return generateNumber;
	}

	public String randomAlphaNumeric()
	{
		String generateString = RandomStringUtils.randomAlphabetic(4);
		String generateNumber = RandomStringUtils.randomNumeric(4);
		return (generateString+"@"+generateNumber);
	}


}




/*

String executionEnv = prop.getProperty("execution_env");  // local / remote

   if (executionEnv.equalsIgnoreCase("local"))
   {
       // ---- Local Execution ----
       switch (br.toLowerCase())
       {
           case "chrome":
               driver = new ChromeDriver();
               break;

           case "edge":
               driver = new EdgeDriver();
               break;

           case "firefox":
               driver = new FirefoxDriver();
               break;

           default:
               System.out.println("No matching local browser");
               return;
       }
   }
   else if (executionEnv.equalsIgnoreCase("remote"))
   {
       // ---- Remote Execution on Selenium Grid ----
       if (br.equalsIgnoreCase("chrome"))
       {
           ChromeOptions options = new ChromeOptions();

           if (os.equalsIgnoreCase("windows")) {
               options.setPlatformName("WIN11");  // WIN11 or WINDOWS
           } else if (os.equalsIgnoreCase("mac")) {
               options.setPlatformName("MAC");
           } else {
               System.out.println("No matching OS for remote execution");
               return;
           }

           driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
       }
       else if (br.equalsIgnoreCase("edge"))
       {
           EdgeOptions options = new EdgeOptions();

           if (os.equalsIgnoreCase("windows")) {
               options.setPlatformName("WIN11");
           } else if (os.equalsIgnoreCase("mac")) {
               options.setPlatformName("MAC");
           } else {
               System.out.println("No matching OS for remote execution");
               return;
           }

           driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
       }
       else if (br.equalsIgnoreCase("firefox"))
       {
           FirefoxOptions options = new FirefoxOptions();

           if (os.equalsIgnoreCase("windows")) {
               options.setPlatformName("WIN11");
           } else if (os.equalsIgnoreCase("mac")) {
               options.setPlatformName("MAC");
           } else {
               System.out.println("No matching OS for remote execution");
               return;
           }

           driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
       }
       else
       {
           System.out.println("No matching remote browser");
           return;
       }
   }
   else
   {
       System.out.println("Invalid execution environment in properties file!");
       return;
   }


   */
