import regexp.Regex;

public class Main {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        //(1*)* 无法计算
        System.out.println(Regex.match("^\\s{2,3}k$", "\r\t\nk"));
        System.out.println(System.currentTimeMillis() - t1);
    }
}
