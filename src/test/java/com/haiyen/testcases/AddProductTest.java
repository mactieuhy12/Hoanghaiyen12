package com.haiyen.testcases;

import com.haiyen.common.BaseTest;
import com.haiyen.dataproviders.DataProviderAddProduct;
import com.haiyen.helpers.ExcelHelper;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddProductTest extends BaseTest {
    ExcelHelper excelLogin;
    ExcelHelper excelAddProduct;

    //Add 2 products in Admin page (Admin role)
    @Test(priority = 1, dataProvider = "data_provider_add_product", dataProviderClass = DataProviderAddProduct.class)
    public void testAddProduct(Hashtable<String, String> data) {
        excelLogin = new ExcelHelper();
        excelAddProduct = new ExcelHelper();
        excelLogin.setExcelFile("DataTest/Login.xlsx", "Login");
        excelAddProduct.setExcelFile("DataTest/AddProduct.xlsx", "AddProduct");
        getAddProductPage().addProduct(excelLogin.getCellData("email", 5), excelLogin.getCellData("password", 5), data.get("productName"), data.get("category"), data.get("unit"), data.get("weight"), data.get("tags"), data.get("unitPrice"), data.get("discountDate"), data.get("quantity"), data.get("description"), data.get("discount"), data.get("image"));
        getAddProductPage().verifyNewProduct(data.get("category"), data.get("unit"), Double.valueOf(data.get("unitPrice")), data.get("description"));

    }

}


