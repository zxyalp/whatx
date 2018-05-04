package com.tmsx.simu;

import com.tmsx.noclient.config.HttpCenterConfig;
import com.tmsx.noclient.simu.MocoClient;
import com.tmsx.noclient.simu.SimuClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


/**
 * Created by yang.zhou on 2017/11/23.
 */
@ContextConfiguration(classes = HttpCenterConfig.class)
public class MocoHttpTest extends AbstractTestNGSpringContextTests{

    @Test
    public void mocoTest() throws Exception{
         new MocoClient().testRequest();
    }

}
