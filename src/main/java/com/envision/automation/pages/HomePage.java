package com.envision.automation.pages;

import com.envision.automation.framework.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver){

        super(driver);


    }

       public HomePage clickOnTshirtMenu() throws IOException {
        clickOnButton("HomePage.BtnT-shirt");
        return this;

}
        public  HomePage clickOnImage() throws IOException {
            clickOnButton("HomePage.clkImage");
            return this;
        }
        public  HomePage clickOnAdd() throws IOException {
            clickOnButton("HomePage.clkAddToCart");
            return clickOnImage();
        }

        public HomePage verifySuccessfulMessage() throws IOException {
            Assert.assertTrue(getWebElementText("HomePage.verifyMessage").contains("Product successfully added to your shopping cart"),"SUCCESSFUL ATTEMPT FAILED");
            return this;
}

public HomePage proceedTo () throws IOException {
        clickOn("HomePage.clkOnCheckOut");
        return  clickOnAdd();
}

public HomePage proceedToCheckout() throws IOException {
        clickOn("HomePage.clkOnProceedToCheckout");
        return  proceedTo();
}

public HomePage clickOnCheckout() throws IOException {
        clickOn("HomePage.clkOnCheck");

        return  proceedToCheckout();
}

public HomePage selectTerms() throws IOException {
        clickOnButton("HomePage.clkOnTerms");

    return proceedToCheckout();
}

public HomePage selectToProceed() throws IOException {
        clickOn("HomePage.clkOnProceedTo");
        return selectTerms();
}

public HomePage clickOnPayment() throws IOException {
        clickOn("HomePage.clkOnWire");
        return selectToProceed();
}

public HomePage confirmOrder() throws IOException {
        clickOn("HomePage.clkOnConfirmOrder");
        return  clickOnPayment();
}










   // public HomePage checkIfSignOutDisplayed() throws IOException {
       // boolean status= isDisplayed("HomePage.lnkSignOut");

       // Assert.assertTrue(status,"element sign out not available");

       // return this;

   // }
    //public String getUserNameLoggedIn() throws IOException {
         //String name =getWebElementText("HomePage.UserLoggedIn");
        //return name;
   // }

   // public HomePage checkIfUserNameLoggedInIsValid(String userLoggedIn) throws IOException {
       // Assert.assertEquals(userLoggedIn,getUserNameLoggedIn());
        //return this;
    //}
}
