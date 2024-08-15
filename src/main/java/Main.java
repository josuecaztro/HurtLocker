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

        StringBuilder sb = new StringBuilder();
        //APPLES
        sb.append(String.format("\nName: Apples%-"+18+"s Seen: %d times","",main.findApplesSeen(fullText)));
        sb.append(String.format("\n=============== %-"+11+"s ================", ""));
        sb.append(String.format("\nPrice: 0.25%-"+19+"s Seen: %d times","",appleMap.get(0.25)));
        sb.append(String.format("\n---------------- %-"+10+"s ----------------", ""));
        sb.append(String.format("\nPrice: 0.23%-"+19+"s Seen: %d times","",appleMap.get(0.23)));
        sb.append("\n\n");
        //COOKIES
        sb.append(String.format("\nName: Cookies%-"+17+"s Seen: %d times","",main.findCookiesSeen(fullText)));
        sb.append(String.format("\n=============== %-"+11+"s ================", ""));
        sb.append(String.format("\nPrice: 2.25%-"+19+"s Seen: %d times","",cookieMap.get(2.25)));
        sb.append(String.format("\n---------------- %-"+10+"s ----------------", ""));
        sb.append("\n\n");
        //MILK
        sb.append(String.format("\nName: Milk%-"+20+"s Seen: %d times","",main.findMilkSeen(fullText)));
        sb.append(String.format("\n=============== %-"+11+"s ================", ""));
        sb.append(String.format("\nPrice: 0.25%-"+19+"s Seen: %d times","",milkMap.get(3.23)));
        sb.append(String.format("\n---------------- %-"+10+"s ----------------", ""));
        sb.append(String.format("\nPrice: 0.23%-"+19+"s Seen: %d times","",milkMap.get(1.23)));
        sb.append("\n\n");
        //BREAD
        sb.append(String.format("\nName: Bread%-"+19+"s Seen: %d times","",main.findBreadSeen(fullText)));
        sb.append(String.format("\n=============== %-"+11+"s ================", ""));
        sb.append(String.format("\nPrice: 0.25%-"+19+"s Seen: %d times","",breadMap.get(1.23)));
        sb.append(String.format("\n---------------- %-"+10+"s ----------------", ""));
        sb.append("\n");
        //ERRORS
        sb.append(String.format("\nErrors:%-"+23+"s Seen: %d times","",4));
        System.out.println(sb);



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
