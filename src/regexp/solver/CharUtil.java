package regexp.solver;

import java.util.ArrayList;
import java.util.Arrays;

class CharUtil {
    private static ArrayList<Character> specials = new ArrayList<>(
            Arrays.asList('s', 'S', 'w', 'W', 'd', 'D', '+', '{', '}', '[', ']', '*', '^', '$', '?', '\\')
    );
    private static ArrayList<Character> escaping = new ArrayList<>(
            Arrays.asList('+', '*', '^', '?', '$', '{', '}', '[', ']', '\\')
    );

    private CharUtil() {
    }

    /**
     * 判断是否匹配正则中的\w
     *
     * @param ch
     * @return
     */
    static boolean isPatternW(char ch) {
        return Character.isDigit(ch) || Character.isLetter(ch) || ch == '_';
    }

    static boolean isWhiteChar(char ch) {
        return ch == '\n' || ch == '\t' || ch == ' ' || ch == '\b' || ch == '\r';
    }

    /**
     * 判断是否是正则中的需要转义的字符
     *
     * @param ch
     * @return
     */
    static boolean isEscaping(char ch) {
        return escaping.contains(ch);
    }

    /**
     * 判断是否是特殊字符
     *
     * @param ch
     * @return
     */
    static boolean isSpecial(char ch) {
        return specials.contains(ch);
    }

    /**
     * 判断是否特殊字符串，例如\s,\w,\d
     *
     * @param s
     * @return
     */
    static boolean isSpecialString(String s) {
        if (s.length() != 2)
            return false;
        return s.charAt(0) == '\\' && isSpecial(s.charAt(1));
    }
}
