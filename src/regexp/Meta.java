package regexp;

class Meta implements Cloneable {
    private String s;
    //字符串s1的下标
    private int i = 0;

    public Meta(String s) {
        this.s = s;
    }

    int i() {
        return i;
    }

    void update(int i) {
        this.i = i;
    }

    boolean ok() {
        return i + 1 < s.length();
    }

    boolean notEnd() {
        return i != s.length();
    }

    public char incr() {
        return s.charAt(i++);
    }

    public char incr(int x){
        char ch = s.charAt(i);
        i += x;
        return ch;
    }

    char cur() {
        return s.charAt(i);
    }

    char pre() {
        return s.charAt(i - 1);
    }

    char next() {
        return s.charAt(i + 1);
    }

    boolean zero() {
        return i == 0;
    }

    char s(int x) {
        return s.charAt(x);
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
