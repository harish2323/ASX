import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Set;

public class ASXBuyRecommendation {
public static int stockcount;
    public static void main(String args[]) throws Exception
    {
        int pages_per_thread=2;

        BuyRecommendationThread []b1=new BuyRecommendationThread[10];

        for(int i=0;i<2;i++) {
           b1[i] =new BuyRecommendationThread(i,(pages_per_thread*i)+1,pages_per_thread*(i+1));


        }

        Thread.sleep(5000);

        WaitUntilThreadsDie();

        System.out.println("Total Stocks Listed : "+stockcount);





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
          //  System.out.println("*****Round*****");
            for (Thread t : threads) {
                String name = t.getName();
             //   System.out.println("Thread name :"+name);
                if(t.getName().startsWith("Thread"))
                {
                    alive=true;
                    continue;

                }

            }
        }

        System.out.println("All threads DEAD !!!");
    }
}
