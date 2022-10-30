package com.envision.automation.pages;

import com.envision.automation.framework.core.BasePage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class loginPage extends BasePage {
    WebDriver driver;

    public loginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }
    public  loginPage enterUserName(String emailId) throws IOException, InterruptedException {
        TypeInto("LoginPage.tbxEmailAddress",emailId);
        return this;
    }
    public loginPage enterPassword(String password) throws IOException, InterruptedException {
        TypeInto("LoginPage.tbxPassword",password);

        return this;
    }

    public HomePage clickOnSignIn() throws IOException {
        clickOn("LoginPage.btnSignIn");
        return new HomePage(driver);

    }
}
