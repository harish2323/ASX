import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class ASXBuyRecommendation {

    public static void main(String args[]) throws Exception
    {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        // options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();



      //  Thread.sleep(5000);
        driver.get("https://www.investing.com/stock-screener/?sp=country::25|sector::a|industry::a|equityType::a%3Ceq_market_cap;1");
        Thread.sleep(5000);
        int size= driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).size();
        System.out.println("Total stocks : "+size);
        String stock_name, stock_url;

       for(int k=1;k<43;k++) {
           driver.get("https://www.investing.com/stock-screener/?sp=country::25|sector::a|industry::a|equityType::a%3Ceq_market_cap;"+k);
           Thread.sleep(3000);
            size= driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).size();
            for (int i = 2; i < size; i++) {
                stock_name = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).get(i).findElement(By.tagName("a")).getText();
                stock_url = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).get(i).findElement(By.tagName("a")).getAttribute("href");
                System.out.println("Stock ::: " + stock_name + "  , URL :: " + stock_url);
            }

        }

        driver.quit();

    }
}
