package regexp.solver;

/**
 * 普通字符串的元数据库
 */
class MetaCommon {
    private String s;
    /**
     * 当前下标
     */
    private int i = 0;
    /**
     * 备份点
     * 用于 | 操作的时候快速回溯。当一个Or操作完全匹配会更新di为i，当进入一个()元组的时候,
     * 会备份改元组的起始点
     */
    private int di = 0;
    /**
     * 贪婪点
     * 用于记录* + ? {} 操作的起始点，默认为不开启，值为-1
     */
    private int gi = 0;

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

    public String incrAndUpdate() {
        String ch = incr();
        update();
        return ch;
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

    //返回下标
    public int i() {
        return i;
    }

    //设置下标
    public void i(int i) {
        this.i = i;
    }

    //返回贪婪点
    public int gi() {
        return gi;
    }

    //设置贪婪下标
    public void gi(int gi) {
        this.gi = gi;
    }

    //移除贪婪下标
    public void giDel() {
        this.gi = -1;
    }

    //是否开启了贪婪模式
    public boolean isGreedy() {
        return gi != -1;
    }

    //返回备份点
    public int di() {
        return di;
    }
    //设置备份点

    public void di(int di) {
        this.di = di;
    }
    //设置备份点为当前i

    public void update() {
        this.di = i;
    }
    //还原备份

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
