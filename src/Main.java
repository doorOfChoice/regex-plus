import regexp.Regexp;

public class Main {
    public static void main(String[] args) {
        Regexp reg = new Regexp("12345....啊$");
        System.out.println(reg.match("123456789啊1"));
    }
}
