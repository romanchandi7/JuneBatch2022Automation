package com.envision.automation.framework.core;

import com.envision.automation.framework.utils.ConfigLoader;
import com.envision.automation.framework.utils.ORUtils;
import com.envision.automation.framework.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    static WebDriver baseDriver;

    public BasePage(WebDriver driver) {
      this.baseDriver =driver;

    }
      public static WebDriver launchBrowser(String browserType){
try {
    if (browserType.equalsIgnoreCase("Chrome")) {
        System.setProperty("webdriver.chrome.driver", ConfigLoader.getChromeDriverPath());
        baseDriver = new ChromeDriver();
    } else if (browserType.equalsIgnoreCase("edge")) {
        System.setProperty("webdriver.edge.driver", ConfigLoader.getEdgeDriverPath());
        baseDriver = new EdgeDriver();
    } else if (browserType.equalsIgnoreCase("firefox")) {
        System.setProperty("webdriver.gecko.driver", ConfigLoader.getFirefoxDriverPath());
        baseDriver = new FirefoxDriver();
    } else {
        throw new UnsupportedOperationException("Browser Type[" + browserType + "]is not supported");
    }
    baseDriver.manage().timeouts().implicitlyWait(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
    baseDriver.manage().window().maximize();
    Reporter.logPassedStep("Browser ["+browserType+"] launched and maximized successfully");
}catch(Exception ex){
    Reporter.logFailedStep("Unable to launch browser["+browserType+"],exception occurred:"+ex);

}
        return baseDriver;
}

        public void launchUrl(String url){
        try{
        this.baseDriver.get(url);
        Reporter.logPassedStep("Url ["+url+"] launched successfully");
        }catch(Exception e){
        Reporter.logFailedStep("unable to launch Url ["+url+"]");
        }}


         public static By getFindBy(String propertyName) throws IOException {
             By by= null;
            try{

             ORUtils.loadORFile();
             String orElementValue = ORUtils.getORPropertyValue(propertyName);

             String byMode=orElementValue.split("#",2)[0];
             String byValue= orElementValue.split("#",2)[1];



             if(byMode.equalsIgnoreCase("id"))
                 by=By.id(byValue);
             else if (byMode.equalsIgnoreCase("name")) {
                 by=By.name(byValue);
             }
             else if (byMode.equalsIgnoreCase("class")) {
                 by=By.className(byValue);
             }
             else if (byMode.equalsIgnoreCase("tag")) {
                 by=By.tagName(byValue);
             }
             else if (byMode.equalsIgnoreCase("css")) {
                 by=By.cssSelector(byValue);
             }
             else if (byMode.equalsIgnoreCase("xpath")) {
                 by=By.xpath(byValue);
             }
             else if (byMode.equalsIgnoreCase("lt")) {
                 by=By.linkText(byValue);
             }
             else if (byMode.equalsIgnoreCase("plt")) {
                 by=By.partialLinkText(byValue);
             }
             else {
                 throw new UnsupportedOperationException("BY Mode value is not supported, check the OR.properties and pass the supported value");
             }

            }catch(Exception e){
             Reporter.logFailedStep("Unable to get by locator ["+by +"]");
                }
            ORUtils.getORPropertyValue("");

        return by;
    }
    public WebElement findWebElement(String orElement) throws IOException {

        WebElement element =null;
        try {

            By by = getFindBy(orElement);
           element= new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.presenceOfElementLocated(by));
         element=   new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
         Reporter.logPassedStep("OR element ["+orElement+"] found successfully");


        }    catch (Exception e){
            Reporter.logFailedStep("Unable to find the element ["+orElement+"]");

        }
        return element;

    }

    public List<WebElement> findWebElements(String orElement) throws IOException {
        List<WebElement> elements =  null;
        try {

            By by = getFindBy(orElement);
            elements= new WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            Reporter.logPassedStep("Elements ["+orElement+"] found successfully");

        }catch(Exception e){
         Reporter.logFailedStep("Unable to locate element ["+orElement+"]");
            }
              return elements;

    }

        public void clickOn(String elementName) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Reporter.logPassedStep("Clicked on element ["+elementName+"] successfully");
        }catch(Exception e){
            Reporter.logFailedStep("Unable to find element ["+elementName+"]");

        }}


    public void clickOnButton(String elementName) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            JavascriptExecutor jse = (JavascriptExecutor) baseDriver;
            jse.executeScript("arguments[0].click();", element);
            Reporter.logPassedStep("Clicked on element ["+elementName+"] successfully");
        }catch(Exception e){
            Reporter.logFailedStep("Unable to find element ["+elementName+"]");

        }}





    public void TypeInto(String elementName, String contentToType) throws IOException, InterruptedException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Thread.sleep(100);
            element.clear();
            Thread.sleep(100);

            element.sendKeys(contentToType);
            Reporter.logPassedStep("Typed ["+contentToType+"] into element ["+elementName+"] successfully");

        }catch (Exception e){
            Reporter.logFailedStep("Unable to  type ["+contentToType+"] into element ["+elementName+"]");


    }}
    public String getWebElementText(String elementName) throws IOException {
        WebElement element=null;
        String text ="";
        try {
             element = findWebElement(elementName);
             text= element.getText();
             Reporter.logPassedStep("Fetched text ["+text+"]from element ["+elementName+"]");

        }catch(Exception e) {
            Reporter.logFailedStep("Unable to fetch text from element ["+elementName+"]");
        }
        return text;
    }

        public String getWebElementAttribute(String elementName, String attributeType) throws IOException {
        WebElement element=null;
        String attributeValue=null;
            try {
                 element = findWebElement(elementName);
                attributeValue= element.getAttribute(attributeType);

                Reporter.logPassedStep("Fetched attribute ["+attributeType+"] value ["+attributeValue+"]");
            } catch (Exception e) {
                Reporter.logFailedStep(" Unable to Fetched attribute ["+attributeType+"] value ");

            }
            return  attributeValue;
        }
        public static void closeBrowser(){
        try {

            baseDriver.close();
            Reporter.logPassedStep("closed browser successfully");
        }catch(Exception e){
            Reporter.logFailedStep("Unable to close the browser");


        }}
    public static void closeAllBrowsers() {
        try{

        baseDriver.quit();
        Reporter.logPassedStep("closed all the browsers and terminate driver session");
    }catch(Exception e){
            Reporter.logFailedStep("Unable to close or terminate driver session");

        }}
    public static void refreshPage() {
        try{

        baseDriver.navigate().refresh();
        Reporter.logPassedStep("web page refreshed");
    }catch(Exception e) {
            Reporter.logFailedStep("web page failed to refresh");
        }
        }

        public String getCurrentUrl(){
        String url= "";
        try{
            url= baseDriver.getCurrentUrl();
            Reporter.logPassedStep("Current Url of page is "+url);
        }catch(Exception e) {
            Reporter.logFailedStep("unable to fetch current Url of page");

        }
            return  url;

        }


    public String getTitleOfPage(){
        String title= "";
        try{
            title= baseDriver.getTitle();
            Reporter.logPassedStep("Title of the page is "+title);
        }catch(Exception e) {
            Reporter.logFailedStep("unable to fetch the title of the page");

        }
        return  title;

    }


    public void selectFromDropdown(String elementName , String how,String valueToSelect) throws IOException {
        try{
        WebElement element = findWebElement(elementName);
        Select dropDownList = new Select(element);

        if(how.equalsIgnoreCase("value")){
            dropDownList.selectByValue(valueToSelect);
        } else if(how.equalsIgnoreCase("visibleText")) {
            dropDownList.selectByVisibleText(valueToSelect);
        } else if(how.equalsIgnoreCase("index")) {
            dropDownList.selectByIndex(Integer.parseInt(valueToSelect));
        }
        Reporter.logPassedStep("Value ["+valueToSelect+"]selected from dropdown["+elementName+"]");
        }catch(Exception e){
            Reporter.logFailedStep(" unable to select Value ["+valueToSelect+"]selece from dropdown ["+elementName+"]");

    }}

    public void selectIndexValueFromDropdown(String elementName, String value) throws IOException {
        try {
            selectFromDropdown(elementName, "index", value);
        }catch(Exception e){

        }

    }
    public void selectVisibleValueFromDropdown(String elementName, String value) throws IOException {
        try{
        selectFromDropdown(elementName, "visibleText", value);
    }catch(Exception e){

        }}

    public void selectValueFromDropdown(String elementName, String value) throws IOException {
        try{
        selectFromDropdown(elementName, "value", value);
    }catch(Exception e){

        }}
    public boolean isDisplayed(String elementName) throws IOException {
        WebElement element=null;
        try{
       element = findWebElement(elementName);
       Reporter.logPassedStep("Element ["+elementName+"] displayed successfully");

    }catch(Exception e) {
            Reporter.logFailedStep("element["+elementName+"] not displayed");

        }
            return element.isDisplayed();
        }}










