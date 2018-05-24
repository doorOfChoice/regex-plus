import regexp.Regex;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();

        Regex reg = new Regex("\\\\");
        System.out.println(reg.match("\\"));

        System.out.println(System.currentTimeMillis() - t1);
    }
}
