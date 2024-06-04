package com.haiyen.testcases;

import com.haiyen.helpers.ExcelHelper;
import com.haiyen.common.BaseTest;
import org.testng.annotations.Test;

public class OrderTest extends BaseTest {
    public ExcelHelper excel;

    @Test(priority = 1)
    public void testOrderProducts() {
        excel = new ExcelHelper();
        excel.setExcelFile("DataTest/Login.xlsx", "Login");
        getOrderPage().order("Giao hàng càng sớm càng tốt", excel.getCellData("email", 4), excel.getCellData("password", 4));
    }
}
