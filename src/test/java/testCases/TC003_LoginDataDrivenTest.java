package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
	public void verify_loginDDT(String email, String pwd, String expRes) {

		logger.info("***** Starting verify_loginDDT ******");

		try {

			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// MyAccount
			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExist();

			/*
			 * Data is valid - login success - test pass - logout - login failed - test fail
			 *
			 * Data is invalid - login success - test fail - logout - login failed - test
			 * pass
			 */

			if (expRes.equalsIgnoreCase("Valid")) {
				if (targetPage == true) {
					map.clickLogout();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}

			if (expRes.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					map.clickLogout();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {
			Assert.fail();
		}

		logger.info("***** Finished verify_loginDDT ******");

	}

}
