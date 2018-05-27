import regexp.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        //(1*)* 无法计算
        System.out.println(Regex.match("^(1*(((12)*)))(4(5(6)))$", "112456"));
//        System.out.println(Pattern.matches("^(2*(24)*)5$", "2245"));
//        Pattern p = Pattern.compi le("^(.{2,}?)2$");
//        Matcher m = p.matcher("ewqe2ewqeq2");
//        if(m.find()) {
//            System.out.println(m.group());
//        }
        System.out.println(System.currentTimeMillis() - t1);
    }
}
