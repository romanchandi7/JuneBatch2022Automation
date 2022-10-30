package com.envision.automation.pages;

import com.envision.automation.framework.core.BasePage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class landingPage  extends BasePage {
    WebDriver driver;
    public landingPage(WebDriver driver){
        super(driver);
        this.driver=driver;
    }

    public landingPage launchAutomationPracticeApplication(){
        launchUrl("https://automationpractice.com/index.php");
        return  this;
    }

    public loginPage clickOnSignIn() throws IOException {
   clickOn("LandingPage.lnkSignIn");
        return new loginPage(driver);
    }
}
