import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class ASXBuyRecommendation {
public static int stockcount;
public static ArrayList<ArrayList<String>> stock_details= new ArrayList<>();
    public static void main(String args[]) throws Exception
    {
        int pages_per_thread=6;


        BuyRecommendationThread []b1=new BuyRecommendationThread[7];

        for(int i=0;i<7;i++) {
          // b1[i] =new BuyRecommendationThread(i,(pages_per_thread*i)+1,pages_per_thread*(i+1));

            b1[i] =new BuyRecommendationThread();
            b1[i].start(i,(pages_per_thread*i)+1,pages_per_thread*(i+1));


        }

        WaitUntilThreadsDie();
        System.out.println("Total Stocks Listed : "+stock_details.size()+"   :  "+stockcount);
        for(int i=0;i<stock_details.size();i++)
        {
            System.out.println("Stock : "+stock_details.get(i));
        }






    }





    private static void WaitUntilThreadsDie()
    {
        //wait until all threads are complete

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println("Alive Thread count : " + threadSet.size());
        // Thread.getAllStackTraces().
        boolean alive=true;
        while(alive) {
            alive=false;
            Set<Thread> threads = Thread.getAllStackTraces().keySet();
           // System.out.println("*****Round*****");
            for (Thread t : threads) {
                String name = t.getName();
                //System.out.println("Thread name :"+name);
                if(t.getName().contains("Thread") || t.getName().contains("Forwarding"))
                {
                    alive=true;
                    continue;

                }

            }
        }

        System.out.println("All threads DEAD !!!");
    }
}
