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
        Main main = new Main();

        //REGEX
        String fullText = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";
        String patternString = "Name:(.*?)[;@*%!^]Price:(.*?)[;@*%!^]type:(.*?)[;@*%!^]expiration:(.*?)#";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fullText);

        //PRICES
        Map<Double, Integer> appleMap = main.createApplesPriceMap(fullText);//0.25 and 0.23
        Map<Double, Integer> cookieMap = main.createCookiePriceMap(fullText);//2.25
        Map<Double, Integer> milkMap = main.createMilkPriceMap(fullText);//3.23 and 1.23
        Map<Double, Integer> breadMap = main.createBreadPriceMap(fullText);//1.23

        // STRING BUILDER
        StringBuilder sb = new StringBuilder();
        addCategory(sb, "Apples", main.findApplesSeen(fullText), appleMap, new double[]{0.25, 0.23});
        addCategory(sb, "Cookies", main.findCookiesSeen(fullText), cookieMap, new double[]{2.25});
        addCategory(sb, "Milk", main.findMilkSeen(fullText), milkMap, new double[]{3.23, 1.23});
        addCategory(sb, "Bread", main.findBreadSeen(fullText), breadMap, new double[]{1.23});
        sb.append(String.format("\nErrors:%-"+23+"s Seen: %d times", "", 4));
        System.out.println(sb);

    }

    private static void addCategory(StringBuilder sb, String name, int seenCount, Map<Double, Integer> priceMap, double[] prices) {
        sb.append(String.format("\nName: %s%-"+18+"s Seen: %d times", name, "", seenCount));
        sb.append(String.format("\n=============== %-"+11+"s ================", ""));
        for (double price : prices) {
            sb.append(String.format("\nPrice: %.2f%-"+19+"s Seen: %d times", price, "", priceMap.getOrDefault(price, 0)));
            sb.append(String.format("\n---------------- %-"+10+"s ----------------", ""));
        }
        sb.append("\n\n");
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
        String normalizedItem = "Bread".toLowerCase();
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
        return priceCounts;
    }

    public Map<Double, Integer> createMilkPriceMap(String text) {
        String normalizedItem = "Milk".toLowerCase();
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

    public Map<Double, Integer> createCookiePriceMap(String text) {
        String normalizedItem = "Cookies".toLowerCase();
        String regex = "\\bnaMe:" + normalizedItem.replace("o", "[o0]") + ";price:(\\d+\\.\\d+);";
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
}
