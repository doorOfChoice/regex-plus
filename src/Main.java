import regexp.Regex;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println(Regex.match("a|b(d|ee)", "bee"));
        System.out.println(System.currentTimeMillis() - t1);
    }
}
