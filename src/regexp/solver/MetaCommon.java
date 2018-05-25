package regexp.solver;

/**
 * 普通字符串的元数据库
 */
class MetaCommon {
    private String s;
    //字符串s1的下标
    private int i = 0;
    //备份下标
    private int di = 0;

    public MetaCommon(String s) {
        this.s = s;
    }

    public boolean ok() {
        return i + 1 < s.length();
    }

    public boolean notEnd() {
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

    public String cur() {
        return s.charAt(i) + "";
    }

    public String pre() {
        return s.charAt(i - 1) + "";
    }

    public String next() {
        return s.charAt(i + 1) + "";
    }

    public char s(int x) {
        return s.charAt(x);
    }

    public String s(int l, int r) {
        return s.substring(l, r);
    }

    public String sDiToI() {
        return s(di, i);
    }

    public int i() {
        return i;
    }

    public int di() {
        return di;
    }


    public void update() {
        this.di = i;
    }

    public void back() {
        this.i = di;
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
