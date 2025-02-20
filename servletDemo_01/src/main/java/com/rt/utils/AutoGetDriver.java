package com.rt.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

/*
* 获取chrome驱动
* */
public class AutoGetDriver {
    private static ChromeDriver driver;
    private final static Properties PROPERTIES=new Properties();
    private static String driver_path;
    static {
        InputStream resourceAsStream = AutoGetDriver.class.getResourceAsStream("/driver.properties");
        try {
            PROPERTIES.load(resourceAsStream);
            driver_path = PROPERTIES.getProperty("chrome.driver_path");
            System.out.println(driver_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChromeDriver getDriver(){
        if(driver==null){
            System.setProperty("webdriver.chrome.driver",driver_path);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000L));
            driver.manage().window().maximize();
        }
        return  driver;
    }
}
