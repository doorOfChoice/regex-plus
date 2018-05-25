import regexp.Regex;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println(Regex.match("^(abc){1,2}1$", "abcabcabc1"));
        System.out.println(System.currentTimeMillis() - t1);
    }
}
