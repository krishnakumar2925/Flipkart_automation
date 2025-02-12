package demo;

import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;
//import dev.failsafe.internal.util.Assert;

public class TestCases {
    ChromeDriver driver;

    

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */

    @Test
    public void testCase01() throws InterruptedException{
        Wrappers wrap =new Wrappers(driver);
        //launch browser
        wrap.launchBrowser("https://www.flipkart.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("flipkart"));
        //search the item
        wrap.searchBox("Washing Machine");
        //sort
        wrap.sortBy("Popularity");
        //get the star less than 4 
        int total = wrap.findTheValue("Stars");
        System.out.println(total);
    }

    @Test
    public void testCase02(){
        Wrappers wrap =new Wrappers(driver);
        //launch browser
        wrap.launchBrowser("https://www.flipkart.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("flipkart"));
        //search the item
        wrap.searchBox("iPhone");
        StringBuilder build =wrap.getTitileAndDiscount(17);
        System.out.println(build);


    }

    @Test
    public void testCase03() throws InterruptedException{
        Wrappers wrap =new Wrappers(driver);
        //launch browser
        wrap.launchBrowser("https://www.flipkart.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("flipkart"));
        //search the item
        wrap.searchBox("Coffee Mug");
        //filter
        wrap.filterSelection("4");
        //cdet the title and image url of the top 5 highest review products
        wrap.topReviewCount();


    }
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}