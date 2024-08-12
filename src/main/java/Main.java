import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.IOException;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);


    }

    public String formatStringData (String x){
        String result = x
                .replaceAll(":", " : ")
                .replaceAll("[;@^*%!]", "\n==========\n");
       return result;
    }

    public String separatePairs(String x) {
        String result = x
                .replaceAll(":", " : ")
                .replaceAll("[;@^*%!]", "\n==========\n")
                .replaceAll("##", "\n-----------\n\n\n");
        String realResult = String.format("%S", result);
        return realResult;
    }
}
