package com.webx;

import com.tmsx.noclient.utils.PropertyUtil;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by yang.zhou on 2017/10/24.
 */
public class AppTest {


    public static void main(String[] args) {
        System.out.println(PropertyUtil.getProperty("simu.user"));

    }
}
