package regexp.old;

import regexp.CharUtil;
import regexp.MetaString;

public class SpecialSolver extends AbstractSolver {
    private String value;

    public SpecialSolver(String value) {
        this.value = value;
    }

    @Override
    public boolean solve(MetaString ms) {
        boolean notEnd = ms.notEnd();
        char ch = notEnd ? ms.cur().charAt(0) : '\0';
        //先判断该特殊字符串是否有特别的意思
        boolean specialResult = notEnd && (value.equals("\\s") && CharUtil.isWhiteChar(ch)
                || value.equals("\\S") && !CharUtil.isWhiteChar(ch)
                || value.equals("\\d") && Character.isDigit(ch)
                || value.equals("\\D") && !Character.isDigit(ch)
                || value.equals("\\w") && CharUtil.isPatternW(ch)
                || value.equals("\\W") && !CharUtil.isPatternW(ch));
        if (specialResult) {
            ms.incr();
            return true;
        }
        //否则判断转义
        return ch == value.charAt(1);
    }

    @Override
    public String toString() {
        return "SpecialSolver[value=" + value + "]";
    }
}