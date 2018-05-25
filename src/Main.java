import regexp.Regex;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println(Regex.match("(a|b)c", "cc"));
        System.out.println(System.currentTimeMillis() - t1);
    }
}
