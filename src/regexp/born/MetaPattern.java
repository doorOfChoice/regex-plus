package regexp.born;

/**
 * 正则字符串的元数据库
 *
 * MetaPattern用于快速的获取普通字符和特殊字符，只要以\开头的都为特殊字符
 * 如果一个字符串为1\23, 则这个字符串会被分割为三个元素, 0, \2, 3
 * 0,1为普通字符, \2为特殊字符
 *
 * 提出转义字符和普通字符的结合，有利于用户构建可扩展的正则表达式
 */
public class MetaPattern implements Cloneable {
    private int i = 0;
    private String s;

    public MetaPattern(String s) {
        this.s = s;
    }

    //判断是否能够移动到下一个索引
    public boolean ok() {
        if (isSpecial())
            return i + 2 < s.length();
        return i + 1 < s.length();
    }

    //是否已经结束
    public boolean notEnd() {
        return i < s.length();
    }

    //向右移动并获取字符串
    public String incr() {
        String r = cur();
        i = i + (isSpecial() ? 2 : 1);
        return r;
    }

    //向右移动x单位并获取字符串
    public String incr(int x) {
        String r = cur();
        for (int i = 0; i < x; ++i)
            incr();
        return r;
    }

    //获取当前字符串
    public String cur() {
        String r;
        if (isSpecial())
            return s.substring(i, i + 2);
        return s.charAt(i) + "";

    }

    //获取前一个字符串
    public String pre() {
        String r;
        if (isSpecial(i - 2))
            r = s.substring(i - 2, i);
        else
            r = s.substring(i - 1, i);
        checkEscape(r);
        return r;
    }

    //获取下一个字符串
    public String next() {
        String r;
        if (isSpecial()) {
            r = isSpecial(i + 2) ? s.substring(i + 2, i + 4) : s.substring(i + 2, i + 3);
        } else
            r = isSpecial(i + 1) ? s.substring(i + 1, i + 3) : s.substring(i + 1, i + 2);
        checkEscape(r);
        return r;
    }

    //获取下x个字符串
    public String next(int x) {
        int j = i;
        int count = 0;
        while (count < x) {
            j += isSpecial(j) ? 2 : 1;
            ++count;
        }
        if (isSpecial(j))
            return s.substring(j, j + 2);
        return s.substring(j, j + 1);
    }



    private boolean isSpecial() {
        return isSpecial(i);
    }

    //判断当前下标处是否为特殊字符
    private boolean isSpecial(int v) {
        return v >= 0 && v + 1 < s.length() && s.charAt(v) == '\\';
    }

    private void checkEscape(String r) {
        if (r.equals("\\"))
            throw new IllegalArgumentException("After \\ u need offer a special character");
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
