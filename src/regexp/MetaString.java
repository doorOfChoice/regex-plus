package regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 普通字符串的元数据库
 */
public class MetaString {
    class Range {
        int min = 0;
        int max = 0;

        @Override
        public String toString() {
            return "[" + min + "," + max + "]";
        }
    }

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
    private Stack<Integer> di = new Stack<>();

    /**
     * 分组范围集合
     * 用户获取分组中起始点和结束点，以获取相应的字符串
     */
    private List<Range> ranges = new ArrayList<Range>();

    public MetaString(String s) {
        this.s = s;
    }

    public void buildRanges(int size) {
        for (int i = 0; i < size; ++i) {
            ranges.add(new Range());
        }
    }

    //获取分组得到的范围列表
    public List<Range> getRanges() {
        return ranges;
    }

    //获取分组得到的字符串
    public List<String> getRangesString() {
        List<String> rs = new ArrayList<>();
        for (Range range : ranges) {
            rs.add(s(range.min, range.max));
        }
        return rs;
    }

    //设置某一组的最小值
    public void setRangesMin(int index, int min) {
        ranges.get(index).min = min;
    }

    //设置某一组的最大值
    public void setRangesMax(int index, int max) {
        ranges.get(index).max = max;
    }


    public boolean ok() {
        return i + 1 < s.length();
    }

    public boolean notEnd() {
        return i != s.length();
    }

    public int incr() {
        return i++;
    }

    public int decr() {
        return i--;
    }

    public String incrAndGet() {
        incr();
        return cur();
    }

    public String decrAndGet() {
        decr();
        return cur();
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
        return s(di.peek(), i);
    }

    //返回下标
    public int i() {
        return i;
    }

    //设置下标
    public void i(int i) {
        this.i = i;
    }


    public int di() {
        return di.peek();
    }

    //di备份
    public void diRestore() {
        i = di.pop();
    }

    //di回档
    public void diSave() {
        di.push(i);
    }

    //设置备份点为当前i
    public void update() {
        di.set(di.size() - 1, i);
    }

    //还原备份
    public void back() {
        this.i = di.peek();
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
