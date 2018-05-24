package regexp;

/**
 * 普通字符串的元数据库
 */
public class MetaCommon {
    private String s;
    //字符串s1的下标
    private int i = 0;

    public MetaCommon(String s) {
        this.s = s;
    }

    boolean ok() {
        return i + 1 < s.length();
    }

    boolean notEnd() {
        return i != s.length();
    }

    public String incr() {
        return s.charAt(i++) + "";
    }

    public String incr(int x) {
        char ch = s.charAt(i);
        i += x;
        return ch + "";
    }

    String cur() {
        return s.charAt(i) + "";
    }

    String pre() {
        return s.charAt(i - 1) + "";
    }

    String next() {
        return s.charAt(i + 1) + "";
    }

    char s(int x) {
        return s.charAt(x);
    }

    int i() {
        return i;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
