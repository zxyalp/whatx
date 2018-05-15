package com.tmsx.noclient.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yang.zhou
 * @date 2018/5/15
 */
public class SimuClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(SimuClientUtils.class);

    private static SimuClientUtils simuClientUtils =null;

    private SimuClientUtils(){}

    public static SimuClientUtils getInstance(){
        if (simuClientUtils == null){
            synchronized (SimuClientUtils.class){
                if (simuClientUtils ==null){
                    simuClientUtils = new SimuClientUtils();
                }
            }
        }
        return simuClientUtils;
    }



}
