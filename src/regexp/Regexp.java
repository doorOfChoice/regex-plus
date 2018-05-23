package regexp;

/**
 * Regexp类负责正则匹配和解析
 * 目前只支持:
 * ^ 匹配开头
 * $ 匹配结尾
 * . 匹配所有字符
 * * 匹配0~n个字符
 * + 匹配1~n个字符
 */
public class Regexp {
    //正规式
    private String pattern;

    public Regexp(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 判断正规式和字符串是否匹配
     *
     * @param str 字符串
     * @return
     */
    public boolean match(String str) {
        Pair pair = new Pair(pattern, str);
        while (pair.v1NotEnd()) {
            if (!handle(pair))
                return false;
        }
        return true;
    }


    private boolean handle(Pair pair) {
        char ch = pattern.charAt(pair.v1());
        //匹配开头
        if (ch == '^') {
            pair.incrV1();
            return true;
        //匹配结尾
        } else if (ch == '$') {
            pair.incrV1();
            return !pair.v2NotEnd();
        //匹配*
        } else if (pair.v1Ok() && pair.v1Next() == '*') {
            return handleStar(pair);
        //匹配+
        } else if (ch == '+') {
            return handlePlus(pair);
        //匹配.和普通字符
        } else if (ch == '.' || (pair.v2NotEnd() && ch == pair.v2Cur())) {
            pair.incrBoth();
            return true;
        }
        return false;
    }

    private boolean handlePlus(Pair pair) {
        //单独的+号没有任何意义，直接判断为错误
        if (pair.v1Zero()) return false;
        char ch1 = pair.v1Pre();
        while (ch1 == '.' || (pair.v2NotEnd() && ch1 == pair.s2(pair.v1()))) pair.incrV2();
        pair.incrV1();
        return true;
    }

    private boolean handleStar(Pair pair) {
        //单独的+号没有任何意义，直接判断为错误
        char ch1 = pair.v1CurAndIncr();
        while (ch1 == '.' || (pair.v2NotEnd() && ch1 == pair.s2(pair.v2()))) pair.incrV2();
        pair.incrV1();
        return true;
    }
}
