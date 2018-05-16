package com.tmsx.simu;

import com.tmsx.noclient.config.HttpCenterConfig;
import com.tmsx.noclient.config.SimuClientConfig;
import com.tmsx.noclient.simu.SimuClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


/**
 * Created by yang.zhou on 2017/11/23.
 */
@ContextConfiguration(classes = HttpCenterConfig.class)
public class SimuHttpTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private SimuClient simuClient;

    @Test
    public void simuTest() throws Exception{
        System.out.println(SimuClientConfig.getAccessToken());
        System.out.println(SimuClientConfig.getLoginResponse());

    }

}
