package regexp.born.solvers;

import regexp.born.MetaString;

import java.util.ArrayList;
import java.util.Arrays;

public class SpecialSolver extends AbstractSolver {
    private static ArrayList<Character> specials = new ArrayList<>(
            Arrays.asList('s', 'S', 'w', 'W', 'd', 'D', '+', '{', '}', '[', ']', '*', '^', '$', '?', '\\')
    );

    private static ArrayList<Character> escaping = new ArrayList<>(
            Arrays.asList('+', '*', '^', '?', '$', '{', '}', '[', ']', '\\')
    );

    private String value;


    public SpecialSolver(String value) {
        this.value = value;
    }

    private static boolean isPatternW(char ch) {
        return Character.isDigit(ch) || Character.isLetter(ch) || ch == '_';
    }

    private static boolean isWhiteChar(char ch) {
        return ch == '\n' || ch == '\t' || ch == ' ' || ch == '\b' || ch == '\r';
    }

    /**
     * 判断是否是正则中的需要转义的字符
     * @param ch
     * @return
     */
    private static boolean isEscaping(char ch) {
        return escaping.contains(ch);
    }

    /**
     * 判断是否是特殊字符
     * @param ch
     * @return
     */
    public static boolean isSpecial(char ch) {
        return specials.contains(ch);
    }

    /**
     * 判断是否特殊字符串，例如\s,\w,\d
     * @param s
     * @return
     */
    public static boolean isSpecialString(String s) {
        if (s.length() != 2)
            return false;
        return s.charAt(0) == '\\' && isSpecial(s.charAt(1));
    }

    @Override
    public boolean solve(MetaString ms) {
        boolean notEnd = ms.notEnd();
        char ch = notEnd ? ms.cur().charAt(0) : '\0';
        //先判断该特殊字符串是否有特别的意思
        boolean specialResult = notEnd && (value.equals("\\s") && isWhiteChar(ch)
                || value.equals("\\S") && !isWhiteChar(ch)
                || value.equals("\\d") && Character.isDigit(ch)
                || value.equals("\\D") && !Character.isDigit(ch)
                || value.equals("\\w") && isPatternW(ch)
                || value.equals("\\W") && !isPatternW(ch));
        if (specialResult) {
            ms.incr();
            return true;
        }
        //否则判断转义
        if(ch == value.charAt(1)) {
            ms.incr();
            return true;
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return solve(ms) && super.solveAndNext(next(), ms);
    }

    @Override
    public String toString() {
        return "SpecialSolver[value=" + value + "]";
    }
}
