import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mainTests {
    Main main = new Main();

    /*
    Instead of : to separate key values, it could be -> (:, @, ^, *, %, !)
    Instead of separating pairs with , it is -> (##)
     */

    @Test
    public void patternMatcherTest(){
        String testCode = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";
        Pattern pattern = Pattern.compile("[:@^*%!]");
        Matcher matcher = pattern.matcher(testCode);
        boolean matchFound = matcher.find();
        Assert.assertTrue(matchFound);
    }

    //##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##

    @Test
    public void makeKeyValueTest(){
        String testCode = "naMe:Milk!price:3.23;type:Food^expiration:1/25/2016";
        String tempString = main.formatStringData(testCode);
        String expected = "naMe : Milk\n" +
                "==========\n" +
                "price : 3.23\n" +
                "==========\n" +
                "type : Food\n" +
                "==========\n" +
                "expiration : 1/25/2016";
        Assert.assertEquals(expected, tempString);
    }


    @Test
    public void separatePairsTest(){
        String testCode = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";
        String tempString = main.separatePairs(testCode);
        String expected = "NAME : MILK\n" +
                "==========\n" +
                "PRICE : 3.23\n" +
                "==========\n" +
                "TYPE : FOOD\n" +
                "==========\n" +
                "EXPIRATION : 1/25/2016\n" +
                "-----------\n" +
                "\n" +
                "\n" +
                "NAME : BREAD\n" +
                "==========\n" +
                "PRICE : 1.23\n" +
                "==========\n" +
                "TYPE : FOOD\n" +
                "==========\n" +
                "EXPIRATION : 1/02/2016\n" +
                "-----------\n\n\n";
        System.out.println(tempString);
        Assert.assertEquals(expected, tempString);
    }

    @Test
    public void addSecondColumn (){
                String format = "%-20s %10S\n";

                System.out.printf(format, "Name:", "Seen:");
                System.out.println("====================");

                System.out.printf(format, "Milk", "6 times");
                System.out.printf(format, "Price: 3.23", "5 times");
                System.out.println("--------------------");
                System.out.printf(format, "Price: 1.23", "1 time");
    }


    @Test
    public void matcherMethodsTest() {
//        Pattern pattern = Pattern.compile("josue");
//        Matcher matcher = pattern.matcher(("Hello josue!"));
//        System.out.println(matcher.);
        String text = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";
        String patternString = ".";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        for (int i = 0; matcher.find(); i++) {
            System.out.println(new StringBuilder()
                    .append("\n-------------------")
                    .append("\nValue = " + matcher.group())
                    .append("\nMatch Number = " + i)
                    .append("\nStarting index = " + matcher.start())
                    .append("\nEnding index = " + matcher.end()));
        }

    }

}
