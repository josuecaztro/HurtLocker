import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);

        String patternString = output;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher("\\w+");

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

    public static int countOccurrences(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int count = 0;

        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public int findBreadSeen(String text) {
        Pattern pattern = Pattern.compile("\\bb[a-zA-Z]*d\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Bread seen: " + count);
        return count;
    }

    public int findApplesSeen(String text) {
        Pattern pattern = Pattern.compile("\\b[aA]pples\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Apples seen: " + count);
        return count;
    }

    public int findMilkSeen(String text) {
        Pattern pattern = Pattern.compile("\\b[mM]ilk\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Milks seen: " + count);
        return count;
    }

    public int findCookiesSeen(String text) {
        Pattern pattern = Pattern.compile("\\b\\w*kies\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Cookies seen: " + count);
        return count;
    }

    public Map<Double, Integer> createBreadPriceMap(String text) {
        String normalizedItem = "Apples".toLowerCase();
        String regex = "\\bnaMe:" + normalizedItem + ";price:(\\d+\\.\\d+);";
        Map<Double, Integer> priceCounts = new HashMap<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String priceStr = matcher.group(1);
            double price = Double.parseDouble(priceStr);
            priceCounts.put(price, priceCounts.getOrDefault(price, 0) + 1);
        }
        return priceCounts;
    }

    public Map<Double, Integer> createApplesPriceMap(String text) {
        String normalizedItem = "Apples".toLowerCase();
        String regex = "\\bnaMe:" + normalizedItem + ";price:(\\d+\\.\\d+);";
        Map<Double, Integer> priceCounts = new HashMap<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String priceStr = matcher.group(1);
            double price = Double.parseDouble(priceStr);
            priceCounts.put(price, priceCounts.getOrDefault(price, 0) + 1);
        }
        System.out.println(priceCounts);
        return priceCounts;
    }
}
