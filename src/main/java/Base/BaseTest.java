package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class BaseTest {
    public static Properties prop;
    public static InputStream fileInputStream;
    public static WebDriver driver;

    public static SoftAssert softAssert;

    @BeforeMethod
    public void setup() {

        try {
            //read properties file
            String propFilePath = System.getProperty("user.dir") + "/src/main/resources/config/config.properties";
            System.out.println(propFilePath);
            fileInputStream = new FileInputStream(propFilePath);
            prop = new Properties();
            softAssert = new SoftAssert();
            prop.load(fileInputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        String browser = prop.getProperty("browser").toUpperCase();
        WebDriverManager.getInstance(DriverManagerType.valueOf(browser)).setup();

        switch (browser) {
            case "CHROME" -> driver = new ChromeDriver();
            case "FIREFOX" -> driver = new FirefoxDriver();
            case "SAFARI" -> driver = new SafariDriver();
            case "EDGE" -> driver = new EdgeDriver();
        }

        Reporter.log("Launching Browser", true);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(prop.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        softAssert.assertAll("Reporting failures for the assertion for `Table` in all of the products titles...");
        Reporter.log("======Browser Closed======");
    }
}
