package com.pm.api.test;

import com.pm.utils.ApiManager;
import org.testng.annotations.Test;

public class ApiTest extends ApiBaseTest {
    ApiManager apiManager = ApiManager.getInstance();

    @Test
    public void testFramework() {
        apiManager.getRequest("BookStore/v1/Books");
        System.out.println(apiManager.getStatusCode());
    }
}
