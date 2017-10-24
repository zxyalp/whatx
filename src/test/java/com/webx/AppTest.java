package com.webx;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by yang.zhou on 2017/10/24.
 */
public class AppTest {

    @BeforeClass
    public void setUpClass(){

    }

    @BeforeMethod
    public void setUp(){

    }


    @Test
    public void testApp(){
        Assert.assertTrue(true);
    }

    @AfterMethod
    public void tearDown(){

    }

    @AfterClass
    public void tearDownClass(){

    }

}
