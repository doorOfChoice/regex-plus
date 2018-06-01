package regexp.born;

import regexp.born.solvers.SpecialSolver;

/**
 * 正则字符串的元数据库
 */
public class MetaPattern implements Cloneable {
    private int i = 0;
    private String s;

    public MetaPattern(String s) {
        this.s = s;
    }

    public boolean ok() {
        if (isSpecial())
            return i + 2 < s.length();
        return i + 1 < s.length();
    }

    public boolean notEnd() {
        return i < s.length();
    }

    public String incr() {
        String r = cur();
        i = i + (isSpecial() ? 2 : 1);
        return r;
    }

    public String incr(int x) {
        String r = cur();
        for (int i = 0; i < x; ++i)
            incr();
        return r;
    }

    public String cur() {
        String r;
        if (isSpecial())
            return s.substring(i, i + 2);
        return s.charAt(i) + "";

    }

    public String pre() {
        String r;
        if (isSpecial(i - 2))
            r = s.substring(i - 2, i);
        else
            r = s.substring(i - 1, i);
        checkEscape(r);
        return r;
    }

    public String next() {
        String r;
        if (isSpecial()) {
            r = isSpecial(i + 2) ? s.substring(i + 2, i + 4) : s.substring(i + 2, i + 3);
        } else
            r = isSpecial(i + 1) ? s.substring(i + 1, i + 3) : s.substring(i + 1, i + 2);
        checkEscape(r);
        return r;
    }

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


    public boolean isSpecial() {
        return isSpecial(i);
    }

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
