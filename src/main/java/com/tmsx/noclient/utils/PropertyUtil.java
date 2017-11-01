package com.tmsx.noclient.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author yang.zhou
 * @date 2017/11/1
 */
public class PropertyUtil {

    private static final Log logger = LogFactory.getLog(PropertyUtil.class);

    private static Properties props;

    static {
        load();
    }

    private synchronized static void load(){
        logger.info("properties loading....");
        props = new Properties();

        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(in);
        }catch (FileNotFoundException e){
            logger.error("文件未找到.",e);
        }catch (IOException i){
            logger.error(i);
        }finally {
            if (null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.info("加载properties文件完成");
            logger.info("文件内容："+props);
        }
    }

    public static String getProperty(String key, String dafaultValue){
        if (props == null){
            load();
        }

        return props.getProperty(key, dafaultValue);
    }

    public static String getProperty(String key){
        if (props == null){
            load();
        }
        return props.getProperty(key);
    }

}
