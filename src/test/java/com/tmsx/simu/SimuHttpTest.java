package com.tmsx.simu;

import com.tmsx.noclient.simu.SimuClient;
import org.testng.annotations.Test;

/**
 * Created by yang.zhou on 2017/11/23.
 */
public class SimuHttpTest {

    @Test
    public void simuTest() throws Exception{
        SimuClient.login();
    }

}
