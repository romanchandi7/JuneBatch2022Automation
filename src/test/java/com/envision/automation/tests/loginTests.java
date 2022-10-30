package com.envision.automation.tests;

import com.envision.automation.framework.core.baseTest;
import com.envision.automation.pages.HomePage;
import com.envision.automation.pages.landingPage;
import com.envision.automation.pages.loginPage;
import org.testng.annotations.Test;

import java.io.IOException;

public class loginTests extends baseTest {
    @Test
    public void validateSuccessfulLoginToApplication() throws IOException, InterruptedException {
        landingPage LandingPage = new landingPage(driver);
        loginPage LoginPage = LandingPage.launchAutomationPracticeApplication().clickOnSignIn();



        HomePage homePage= LoginPage
                .enterUserName("hey@abc.com")
                .enterPassword("Testing@1234")
                .clickOnSignIn();

        homePage.clickOnTshirtMenu().clickOnImage().clickOnAdd().proceedTo()
                .proceedToCheckout().clickOnCheckout().selectTerms()
                .selectToProceed().clickOnPayment().confirmOrder();


        //homePage.checkIfSignOutDisplayed()
               // .checkIfUserNameLoggedInIsValid("Severus Snape");



    }

}
