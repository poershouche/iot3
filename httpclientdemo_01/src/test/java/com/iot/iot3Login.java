package com.iot;

import org.opencv.core.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.time.Duration;

public class iot3Login {

//    static {
//        // 加载 OpenCV 库
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }

    public static void main(String[] args) {


        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver", "E:\\testDemo\\javaDemo\\htttpclinedemo_01\\src\\main\\resources\\chromedriver.exe");

        // 初始化 WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // 打开目标页面
            driver.get("https://unilink.crland.com.cn/iot-ui/#/user/login");

            // 等待页面加载
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10000L));

            // 定位账号与密码
            WebElement username = driver.findElement(By.xpath("//input[@id='form_item_username' and @class='ant-input'"));
            WebElement password = driver.findElement(By.className("ant-input-affix-wrapper ant-input-password"));

            username.clear();
            username.sendKeys("fb-50");

            password.clear();
            password.sendKeys("Zhangsiqi1996!#");

            // 点击登录
            driver.findElement(By.className("ant-btn ant-btn-primary ant-btn-block custom-button login-form-button")).click();

            // 等待请求canvas出来
            Thread.sleep(2000L);
            // 定位 Canvas 元素
            WebElement canvas = driver.findElement(By.tagName("canvas"));

            // 截取 Canvas 图像并保存
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File canvasImage = new File("canvas.png");
            screenshot.renameTo(canvasImage);

            // 使用 OpenCV 处理图像
            Mat sourceImage = Imgcodecs.imread(canvasImage.getAbsolutePath());
            Mat templateImage = Imgcodecs.imread("path/to/template.png"); // 滑块模板图像

            // 图像匹配
            Mat result = new Mat();
            Imgproc.matchTemplate(sourceImage, templateImage, result, Imgproc.TM_CCOEFF_NORMED);

            // 获取匹配结果
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
            Point matchLoc = mmr.maxLoc;

            // 计算滑块位置
            int sliderX = (int) matchLoc.x;
            int sliderY = (int) matchLoc.y;

            // 定位滑块按钮
            WebElement sliderButton = driver.findElement(By.id("slider-button"));

            // 模拟拖动操作
            Actions actions = new Actions(driver);
            actions.clickAndHold(sliderButton)
                    .moveByOffset(sliderX, 0) // 拖动到目标位置
                    .release()
                    .perform();

            // 等待验证完成
            Thread.sleep(2000);

            // 检查是否验证成功
            if (driver.getCurrentUrl().equals("https://example.com/dashboard")) {
                System.out.println("验证成功！");
            } else {
                System.out.println("验证失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}