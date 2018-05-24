package regexp;

/**
 * Regexp类负责正则匹配和解析
 * 目前只支持:
 * ^ 匹配开头
 * $ 匹配结尾
 * . 匹配所有字符
 * * 匹配0~n个字符
 * + 匹配1~n个字符
 * ? 匹配0~1个字符
 * \s 匹配空白字符串  \S 非空白
 * \d 匹配数字 \D 非数字
 * \w 匹配数字字母_ \W 不匹配数字字母_
 */
public class Regex {
    //正规式
    private String pattern;

    public Regex(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 判断正规式和字符串是否匹配
     *
     * @param str 字符串
     * @return
     */
    public boolean match(String str) {
        MetaPattern mp = new MetaPattern(pattern);
        MetaCommon ms = new MetaCommon(str);
        while (mp.notEnd()) {
            if (!handle(mp, ms))
                return false;
        }
        return true;
    }

    /**
     * @param mp 正规式的元数据
     * @param ms 字符串的元数据
     * @return
     */
    private boolean handle(MetaPattern mp, MetaCommon ms) {
        String ch = mp.cur();
        if (ch.equals("^")) { //匹配开头
            mp.incr();
            return true;
        } else if (ch.equals("$")) { //匹配结尾
            mp.incr();
            return !ms.notEnd();
        } else if (mp.ok() && mp.next().equals("?")) {
            return handleQuestion(mp, ms);
        } else if (mp.ok() && mp.next().equals("*")) { //匹配0-n
            return handleStar(mp, ms);
        } else if (ch.equals("+")) { //判断1-n
            return handlePlus(mp, ms);
        } else if (mp.isSpecial()) { //判断特殊字符
            boolean result = handleSpecial(mp.incr(), ms);
            ms.incr();
            return result;
        } else if (ch.equals(".") || (ms.notEnd() && ch.equals(ms.cur()))) { //匹配普通字符
            mp.incr();
            ms.incr();
            return true;
        }
        return false;
    }

    private boolean handlePlus(MetaPattern mp, MetaCommon ms) {
        //单独的+号没有任何意义，直接判断为错误
        if (mp.isZero()) throw new IllegalArgumentException("The pattern should not be just one +");
        String ch = mp.pre();
        boolean isSpecial = CharUtil.isSpecialString(ch);
        while (ch.equals(".")
                || (isSpecial && handleSpecial(ch, ms))
                || (ms.notEnd() && ch.equals(ms.cur()))
                ) ms.incr();
        mp.incr();
        return true;
    }

    private boolean handleStar(MetaPattern mp, MetaCommon ms) {
        String ch = mp.cur();
        boolean isSpecial = CharUtil.isSpecialString(ch);
        while (ch.equals(".")
                || (isSpecial && handleSpecial(ch, ms))
                || (ms.notEnd() && ch.equals(ms.cur()))) ms.incr();
        mp.incr(2);
        return true;
    }

    private boolean handleQuestion(MetaPattern mp, MetaCommon ms) {
        String ch = mp.cur();
        if (ch.equals(".")
                || (mp.isSpecial() && handleSpecial(ch, ms))
                || (ms.notEnd() && ch.equals(ms.cur()))) ms.incr();
        mp.incr(2);
        return true;
    }

    /**
     * 判断普通字符是否符合正则的特殊匹配规则
     * 在调用这个之前必须判断是否是special字符串
     *
     * @param s
     * @param ms
     * @return
     */
    private boolean handleSpecial(String s, MetaCommon ms) {
        boolean notEnd = ms.notEnd();
        char ch = notEnd ? ms.cur().charAt(0) : '\0';
        //先判断该特殊字符串是否有特别的意思
        boolean specialResult = notEnd && (s.equals("\\s") && Character.isSpaceChar(ch)
                || s.equals("\\S") && !Character.isSpaceChar(ch)
                || s.equals("\\d") && Character.isDigit(ch)
                || s.equals("\\D") && !Character.isDigit(ch)
                || s.equals("\\w") && CharUtil.isPatternW(ch)
                || s.equals("\\W") && !CharUtil.isPatternW(ch));
        if (specialResult) {
            return true;
        }
        //否则判断转义
        return ch == s.charAt(1);
    }


}
