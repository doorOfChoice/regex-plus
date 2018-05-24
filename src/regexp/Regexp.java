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
        Meta mp = new Meta(pattern);
        Meta ms = new Meta(str);
        while (mp.notEnd()) {
            if (!handle(mp, ms))
                return false;
        }
        return true;
    }

    /**
     *
     * @param mp 正规式的元数据
     * @param ms 字符串的元数据
     * @return
     */
    private boolean handle(Meta mp, Meta ms) {
        char ch = mp.cur();
        //匹配开头
        if (ch == '^') {
            mp.incr();
            return true;
        } else if (ch == '$') {
            mp.incr();
            return !ms.notEnd();
        } else if (mp.ok() && mp.next() == '*') {
            return handleStar(mp, ms);
        } else if (ch == '+') {
            return handlePlus(mp, ms);
        } else if (ch == '.' || (ms.notEnd() && ch == ms.cur())) {
            mp.incr();
            ms.incr();
            return true;
        }
        return false;
    }

    private boolean handlePlus(Meta mp, Meta ms) {
        //单独的+号没有任何意义，直接判断为错误
        if (mp.zero()) return false;
        char ch1 = mp.pre();
        while (ch1 == '.' || (ms.notEnd() && ch1 == ms.cur())) ms.incr();
        mp.incr();
        return true;
    }

    private boolean handleStar(Meta mp, Meta ms) {
        char ch1 = mp.incr();
        while (ch1 == '.' || (ms.notEnd() && ch1 == ms.cur())) ms.incr();
        mp.incr();
        return true;
    }
}
