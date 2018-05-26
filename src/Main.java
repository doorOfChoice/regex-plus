import regexp.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println(Regex.match("^1{3,}$", "11111111"));
//        System.out.println(Pattern.matches("^[abc]e$", "be"));
//        Pattern p = Pattern.compile("[(a)]");
//        Matcher m = p.matcher("a");
//        if(m.find()) {
//            System.out.println(m.group(1));
//        }
        System.out.println(System.currentTimeMillis() - t1);
    }
}
