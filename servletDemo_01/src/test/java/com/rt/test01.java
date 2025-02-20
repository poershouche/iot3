package com.rt;

import com.beust.jcommander.Parameter;
import org.junit.jupiter.params.ParameterizedTest;
import com.rt.utils.AutoGetDriver;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class test01 {
    @Test
    @Order(1)
    public void test() {
        ChromeDriver driver = AutoGetDriver.getDriver();
        driver.get("http://www.baidu.com");
        String text = driver.findElement(By.cssSelector(".title")).getText();
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMillis(1));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("")));
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
        }

        Assertions.assertEquals("百度一下", text);
        try {
            Thread.sleep(4000L);
            driver.quit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @ParameterizedTest
    @CsvFileSource(resources = {}, emptyValue = "xxx.csv")
    public void test01(String name, String address) {
        ChromeDriver driver = AutoGetDriver.getDriver();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String format = simpleDateFormat.format(System.currentTimeMillis());
        String substring = format.substring(0, 6);
        System.out.println(substring);
        File file = new File("./src/main/resources/file/" + substring + ".png");
        System.out.println(file);
        System.out.println(file.exists());
        System.out.println(file.canExecute());
        File screenshotAs = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotAs, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
