import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BuyRecommendationThread extends ASXBuyRecommendation implements Runnable {
    public static int start_count,end_count,thread_count;
    public BuyRecommendationThread(int threadcount,int start, int end) {
        this.start_count=start;
        this.end_count=end;
        this.thread_count=threadcount;
        Thread t= new Thread();
        t.start();

    }

    public void run() {
        try {
            int count=0;
            System.out.println("Thread "+this.thread_count+" Starting **********************************************");
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            // options.addArguments("headless");
            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();


            //  Thread.sleep(5000);
           // driver.get("https://www.investing.com/stock-screener/?sp=country::25|sector::a|industry::a|equityType::a%3Ceq_market_cap;1");
           // Thread.sleep(5000);
            //int size = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).size();
          //  System.out.println("Total stocks : " + size);
            driver.get("https://www.investing.com/stock-screener/?sp=country::25|sector::a|industry::a|equityType::a%3Ceq_market_cap;1");
            String stock_name, stock_url;

            for (int k = this.start_count; k <= this.end_count; k++) {
                driver.get("https://www.investing.com/stock-screener/?sp=country::25|sector::a|industry::a|equityType::a%3Ceq_market_cap;" + k);
                Thread.sleep(3000);
               int size = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).size();
                for (int i = 2; i < size; i++) {
                    stock_name = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).get(i).findElement(By.tagName("a")).getText();
                    stock_url = driver.findElements(By.xpath("//*[@data-column-name='name_trans']")).get(i).findElement(By.tagName("a")).getAttribute("href");
                    System.out.println("Thread "+this.thread_count+" "+(count+1)+") Stock ::: " + stock_name + "  , URL :: " + stock_url);
                    count++;
                    BuyRecommendationThread.stockcount++;
                }

            }

            driver.quit();
           // BuyRecommendationThread.stockcount=count;

        } catch (Exception e) {
        }

    }
}