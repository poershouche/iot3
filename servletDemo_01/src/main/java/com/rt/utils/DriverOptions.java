package com.rt.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum DriverOptions {

    CHROME("webdriver.chrome.driver","driver"),
    FIREFOX("webdriver.firefox.driver", "driver"),
    EDGE("webdriver.firefox.driver","driver"),
    IE("webdriver.firefox.driver","driver");


    private final String webdriver;
    private  final String driver;




     DriverOptions(String webdriver, String driver) {
        this.webdriver = webdriver;
        this.driver = driver;
    }

    public String getWebdriver() {
        return webdriver;
    }

    public String getDriver() {
        return driver;
    }
}
