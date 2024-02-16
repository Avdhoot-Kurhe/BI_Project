package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {

    public static ChromeDriver createDriver() {
        // Use WebDriverManager to setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Optional: Add any desired ChromeOptions
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Example: Run Chrome in headless mode

        // Create a new ChromeDriver instance with options
        return new ChromeDriver(options);
    }

    public static void printQKartLoadingtime(ChromeDriver driver) {
        // Navigate to home page of QKart
        driver.get("https://crio-qkart-frontend-qa.vercel.app/");

        // Get the start time
        long startTime = System.currentTimeMillis();

        // TODO: Perform any actions on the page to trigger loading (if needed)

        // Get the end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the page loading time
        long pageLoadTime = endTime - startTime;
        System.out.println("QKart page loading time: " + pageLoadTime + " milliseconds");
    }

    public static void captureFullPageScreenshot(ChromeDriver driver) {
        // TODO: Capture the full page screenshot
        // Save the file with a unique name

        // Create a variable that holds the current timestamp (used for generating a unique file name)
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Create an instance of Ashot object
        AShot ashot = new AShot();

        // Capture a full page screenshot
        Screenshot screenshot = ashot.shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);

        // Save the captured file with a unique name
        String screenshotPath = "D:\\Docker\\BI\\app\\Image\\" + timeStamp + ".png"; // Change this to the desired path

        try {
            // Save the screenshot to the specified path
            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotPath));

            // Print the path of the saved screenshot
            System.out.println("AShot Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Error saving AShot screenshot: " + e.getMessage());
        }
    }

    public static void GetProductImageandURL(ChromeDriver driver, String productName) throws InterruptedException {
        // TODO: Given the product name, print the price of the product and the URL of
        // the image
        // Example: Find the element containing the product price and image URL
        driver.findElement(By.xpath("(//input[@name='search'])[1]")).sendKeys(productName);
        Thread.sleep(2000);
        String productPrice = driver.findElement(By.xpath("(//div[@class='MuiCardContent-root css-1qw96cp']//p)[2]"))
                .getText();
        String imageURL = driver.findElement(By.tagName("img")).getAttribute("src");

        System.out.println("Product: " + productName);
        System.out.println("Price: " + productPrice);
        System.out.println("Image URL: " + imageURL);
    }

    public static void main(String[] args) {
        ChromeDriver driver = createDriver();
        String productName = "YONEX"; // Example product name

        try {
            printQKartLoadingtime(driver);
            captureFullPageScreenshot(driver);
            GetProductImageandURL(driver, productName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
