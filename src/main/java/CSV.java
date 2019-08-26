import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class CSV {

    public static void main(String args[]) throws Exception
    {
        String newFileName = "Bitcoin.csv";
        File newFile = new File(newFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile,true));

       // writer.write("open,close,trading signal");
        writer.newLine();
        writer.append("jjjj,ronaldo,ronaldo");
        writer.flush();
        writer.close();

    }
}
