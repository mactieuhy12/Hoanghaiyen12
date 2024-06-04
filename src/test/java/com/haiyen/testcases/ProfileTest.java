package com.haiyen.testcases;

import com.haiyen.common.BaseTest;
import com.haiyen.helpers.ExcelHelper;
import com.haiyen.pages.LoginPage;
import org.testng.annotations.Test;

public class ProfileTest extends BaseTest {

    public ExcelHelper excel;

    @Test(priority = 1)
    public void testUpdateProfile() {
        excel = new ExcelHelper();
        excel.setExcelFile("DataTest/Login.xlsx", "Login");
        LoginPage loginPage = new LoginPage();
        loginPage.loginSuccessWithCustomerAccount(excel.getCellData("email", 4), excel.getCellData("password", 4));
        getProfilePage().updateProfile();
    }
//đổi mật khẩu
//    @Test(priority = 2)
//    public void updateEmail() {
//        excel = new ExcelHelper();
//        excel.setExcelFile("DataTest/Login.xlsx", "Login");
//        getProfilePage().updateEmail(excel.getCellData("email", 4), excel.getCellData("password", 4));
//    }

    @Test(priority = 3)
    public void testAddNewAddress() {
        excel = new ExcelHelper();
        excel.setExcelFile("DataTest/Login.xlsx", "Login");
        LoginPage loginPage = new LoginPage();
        loginPage.loginSuccessWithCustomerAccount(excel.getCellData("email", 4), excel.getCellData("password", 4));
        getProfilePage().addNewAddress();
    }
}
//cập nhập thông tin cá nhân