import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BTC {

    public static void main(String args[]) throws Exception {
        String newFileName = "./Data/Bitcoin_"+new Date().toString()+".csv";
        File newFile = new File(newFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile,true));
        writer.write("open,close,trading signal");
        writer.flush();

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.investing.com/crypto/bitcoin/btc-usd");
        ArrayList<ArrayList<Float>> price= new ArrayList();
        ArrayList<Float>trend_bar=new ArrayList<>();
        Float temp_price_open,temp_price_close;
        Float profit_loss=new Float(0);
        Float buy_price=new Float(0);
        Float sell_price= new Float(0);

        String trend="",trend_bar_status="";
        String trade="Trading not started yet";
        //Thread.sleep(5000);

        System.out.println("Starting to Analyse . . .");
        int i=0;
        while(i<240 || !trade.equals("Buy")) {
            temp_price_open=Float.valueOf(driver.findElement(By.id("last_last")).getText().replace(",",""));
            Thread.sleep(120000);
            temp_price_close=Float.valueOf(driver.findElement(By.id("last_last")).getText().replace(",",""));
            ArrayList<Float> current_price=new ArrayList<>();
            current_price.add(0,temp_price_open);
            current_price.add(1,temp_price_close);

            price.add(current_price);

            if(temp_price_close>temp_price_open)
                trend="Up";
            else
                trend="Down";



            if(i==0)
            {
                trend_bar.add(0,temp_price_open);
                trend_bar.add(1,temp_price_close);
                //if close price is greater than open price then its a up trend
                if(trend_bar.get(1)>trend_bar.get(0))
                    trend_bar_status="Up";
                else
                    trend_bar_status="Down";
            }

            else
            {
                //Buy or Sell
                if(trend.equals("Up"))
                {
                    if(trend_bar_status.equals("Down"))
                    {
                        if(temp_price_close>trend_bar.get(0) && !trade.equals("Buy")) {
                            trade = "Buy";
                            buy_price=temp_price_close;
                            System.out.println("Bought at :"+buy_price);
                        }
                    }
                    if(trend_bar_status.equals("Up") && !trade.equals("Buy"))
                    {
                        if(temp_price_close>trend_bar.get(0)) {
                            trade = "Buy";
                            buy_price=temp_price_close;
                            System.out.println("Bought at :"+buy_price);


                        }
                    }

                }
                if(trend.equals("Down"))
                {
                    if(trend_bar_status.equals("Up"))
                    {
                        if(temp_price_close<trend_bar.get(0) && trade.equals("Buy")) {

                            trade = "Sell";
                            sell_price=temp_price_close;
                            System.out.println("Sold at :"+sell_price);

                            profit_loss=profit_loss+(sell_price-buy_price);
                            sell_price=new Float(0);
                            buy_price=new Float(0);

                        }

                    }
                }
                ////// End of Buy/Sell section

                //// Set the trend bar
                //if the trend bar status is up
                if(trend_bar_status.equals("Up"))
                {
                    if(trend.equals("Up"))
                    {
                        if(temp_price_close>trend_bar.get(1))
                        {
                            trend_bar=new ArrayList<>();
                            trend_bar.add(0,temp_price_open);
                            trend_bar.add(1,temp_price_close);
                            trend_bar_status="Up";

                        }
                    }
                    if(trend.equals("Down"))
                    {
                        if(temp_price_close<trend_bar.get(0))
                        {
                            trend_bar=new ArrayList<>();
                            trend_bar.add(0,temp_price_open);
                            trend_bar.add(1,temp_price_close);
                            trend_bar_status="Down";
                        }
                    }
                }

                //if trend bar status is down
                else
                {
                    if(trend.equals("Up"))
                    {
                        if(temp_price_close>trend_bar.get(0))  // current close price is greater than the open price down ward trend bar
                        {
                            trend_bar=new ArrayList<>();
                            trend_bar.add(0,temp_price_open);
                            trend_bar.add(1,temp_price_close);
                            trend_bar_status="Up";
                        }
                    }
                    if(trend.equals("Down"))
                    {
                        if(temp_price_close<trend_bar.get(1))
                        {
                            trend_bar=new ArrayList<>();
                            trend_bar.add(0,temp_price_open);
                            trend_bar.add(1,temp_price_close);
                            trend_bar_status="Down";
                        }
                    }
                }



            }
            writer.newLine();
            writer.append(temp_price_open+","+temp_price_close+","+trade);
            writer.flush();


            System.out.println("Open :"+temp_price_open+" Close :"+temp_price_close+" Trend :"+trend +" *** Trend Bar ::  Open :"+trend_bar.get(0)+
                    "  Close :"+trend_bar.get(1)+"  Trend bar status :"+trend_bar_status +" Trading status :"+trade +" Profit/Loss : "+profit_loss);
        i++;

        }
        driver.quit();

      System.out.println("Total profit/loss :"+profit_loss);
        writer.close();


    } // End of main



}
