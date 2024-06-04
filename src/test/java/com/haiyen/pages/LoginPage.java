package com.haiyen.pages;

import com.haiyen.helpers.PropertiesHelper;
import com.haiyen.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage extends CommonPage {

    public By closeAdvertisementPopup = By.xpath("//i[@class='la la-close fs-20']");
    private By buttonLogin = By.xpath("//a[normalize-space() = 'Login' and @class = 'text-reset d-inline-block opacity-60 py-2']");
    private By buttonCopyAdminAcc = By.xpath("//button[normalize-space()='Copy']");
    private By buttonSubmitLogin = By.xpath("//button[normalize-space()='Login']");
    private By titleLoginPage = By.xpath("//h1[normalize-space() = 'Login to your account.']");
    private By messageRequiredEmail = By.xpath("//strong[contains(text(),'The email field is required when phone is not present.')]");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By messageAccDoesNotExist = By.xpath("//span[@data-notify='message']");
    private By messageRequiredPassword = By.xpath("//input[contains(@class, 'is-invalid') and @id = 'password']");
    private By titleAnhTesterAdminPage = By.xpath("//img[@alt='Active eCommerce CMS']");
    private By buttonOkCookies = By.xpath("//button[normalize-space()='Ok. I Understood']");

    public void openLoginPage() {
        WebUI.openURL(PropertiesHelper.getValue("URL"));
        WebUI.clickElement(closeAdvertisementPopup);
        WebUI.clickElement(buttonOkCookies);
        WebUI.clickElement(buttonLogin);
        WebUI.waitForPageLoaded();
        WebUI.verifyAssertTrueIsDisplayed(titleLoginPage, "Login page is NOT displayed");
    }

    public void openHomePage() {
        WebUI.openURL(PropertiesHelper.getValue("URL"));
        WebUI.clickElement(closeAdvertisementPopup);
        WebUI.clickElement(buttonOkCookies);
        WebUI.waitForPageLoaded();
    }

    public void loginFailWithEmailNull() {
        openLoginPage();
        WebUI.sleep(1);
        WebUI.setText(inputPassword, "123456");
        WebUI.sleep(1);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.verifyAssertTrueIsDisplayed(messageRequiredEmail, "Error Message không xuất hiện.");
        WebUI.verifyEquals(WebUI.getElementText(messageRequiredEmail), "The email field is required when phone is not present.", "Nội dung của message không đúng.");
        WebUI.sleep(2);
    }

    public void loginFailWithEmailDoesNotExist(String email, String password) {
        openLoginPage();
        WebUI.setTextAndClear(inputEmail, email);
        WebUI.setTextAndClear(inputPassword, password);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.verifyAssertTrueIsDisplayed(messageAccDoesNotExist, "Error Message không xuất hiện.");
        Assert.assertEquals(WebUI.getElementText(messageAccDoesNotExist), "Invalid login credentials", "Nội dung của message không đúng.");
    }

    public void loginFailWithNullPassword(String email) {
        openLoginPage();
        WebUI.setTextAndClear(inputEmail, email);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.verifyAssertTrueIsDisplayed(messageRequiredPassword, "Password is NULL but valid is NOT displayed.");
    }

    public void loginFailWithIncorrectPassword(String email, String password) {
        openLoginPage();
        WebUI.setTextAndClear(inputEmail, email);
        WebUI.clearText(inputPassword);
        WebUI.setTextAndClear(inputPassword, password);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.verifyAssertTrueIsDisplayed(messageAccDoesNotExist, "Password is failed but valid is NOT displayed.");
    }

    public void loginSuccessWithCustomerAccount(String email, String password) {
        openLoginPage();
        WebUI.setTextAndClear(inputEmail, email);
        WebUI.clearText(inputPassword);
        WebUI.setTextAndClear(inputPassword, password);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.waitForElementVisible(DashboardPage.titleDashboard);
        WebUI.verifyAssertTrueIsDisplayed(DashboardPage.titleDashboard, "Dashboard page is NOT displayed.");
        WebUI.verifyEquals(WebUI.getElementText(DashboardPage.titleDashboard), "Dashboard");
    }

    public void loginSuccessAdminPage(String email, String password) {
        WebUI.openURL(PropertiesHelper.getValue("URL_ADMIN"));
        WebUI.setTextAndClear(inputEmail, email);
        WebUI.setTextAndClear(inputPassword, password);
        WebUI.clickElement(buttonSubmitLogin);
        WebUI.waitForElementVisible(titleAnhTesterAdminPage);
        WebUI.verifyAssertTrueIsDisplayed(titleAnhTesterAdminPage, "Admin page is NOT displayed.");
    }
}


