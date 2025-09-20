package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {



	@Test(groups = {"Regression","Master"})
	public void verify_account_registration()
	{

		logger.info("***** Starting TC001_AccountRegistrationTest *****");

		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on My Account link");

		hp.clickRegister();
		logger.info("Clicked on Register link");

		AccountRegistrationPage arp = new AccountRegistrationPage(driver);

		logger.info("Providing Customer Details......");

		/*
		 // This is use for same randomString in firstname,lastname and email
		String randomString = randomString();
		arp.setFirstName(randomString);
		arp.setLastName(randomString);
		arp.setEmail(randomString+"@gmail.com");
		*/

		String randomString = randomString();

		arp.setFirstName(randomString.toUpperCase());
		arp.setLastName(randomString().toUpperCase());
		arp.setEmail(randomString.toLowerCase()+"@gmail.com");
		arp.setTelephone(randomNumber());

		String password = randomAlphaNumeric();

		arp.setPassword(password);
		arp.setConfirmPassword(password);

		arp.setPrivacyPolicy();
		arp.clickContinue();

		logger.info("Validating Expected message....");
		String confMsg = arp.getConfirmationMsg();

		if(confMsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed....");
			logger.debug("Debug logs....");
			Assert.assertTrue(false);
		}



		}catch(Exception e) {

			Assert.fail();
		}

		logger.info("***** Finished TC001_AccountRegistrationTest *****");



	}

}
