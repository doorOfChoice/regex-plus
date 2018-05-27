import regexp.Regex;

public class Main {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        //(1*)* 无法计算
        System.out.println(Regex.match("^(1{3,}(12)*(3(4(5))))$", "11112345"));
        System.out.println(System.currentTimeMillis() - t1);
    }
}
