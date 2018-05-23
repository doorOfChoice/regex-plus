package regexp;

class Pair implements Cloneable {
    private String s1;
    private String s2;
    //字符串s1的下标
    private int v1 = 0;
    //字符串s2的下标
    private int v2 = 0;

    public Pair(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }


    int v1() {
        return v1;
    }

    int v2() {
        return v2;
    }

    void update(int v1, int v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public void incrV1() {
        ++v1;
    }

    public void incrV2() {
        ++v2;
    }

    public void incrBoth() {
        ++v1;
        ++v2;
    }

    boolean v1Ok() {
        return v1 + 1 < s1.length();
    }

    boolean v1NotEnd() {
        return v1 != s1.length();
    }

    boolean v2Ok() {
        return v2 + 1 < s2.length();
    }

    boolean v2NotEnd() {
        return v2 != s2.length();
    }

    char v1Cur() {
        return s1.charAt(v1);
    }

    char v1Pre() {
        return s1.charAt(v1 - 1);
    }

    char v1Next() {
        return s1.charAt(v1 + 1);
    }

    char v1CurAndIncr() {
        return s1.charAt(v1++);
    }

    boolean v1Zero() {
        return v1 == 0;
    }

    char v2Cur() {
        return s2.charAt(v2);
    }

    char v2CurAndIncr() {
        return s2.charAt(v2++);
    }

    char v2Pre() {
        return s2.charAt(v2 - 1);
    }

    char v2Next() {
        return s2.charAt(v2 + 1);
    }

    char s1(int x) {
        return s1.charAt(x);
    }

    char s2(int x) {
        return s2.charAt(x);
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
