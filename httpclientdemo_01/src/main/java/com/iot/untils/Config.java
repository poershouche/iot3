package com.iot.untils;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 */

public class Config {
    private static Properties properties;
    static {
        InputStream resourceAsStream = Config.class.getResourceAsStream("/config.properties");

        try {
            properties = new Properties();
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl(){
        return properties.getProperty("iot3.iot-prod");
    }
    public static String getGrantType(){
        return properties.getProperty("iot3.grant_type");
    }
    public static String getClientId(){
        return properties.getProperty("iot3.client_id");
    }
    public static String getClientSecret(){
        return properties.getProperty("iot3.client_secret");
    }
}


