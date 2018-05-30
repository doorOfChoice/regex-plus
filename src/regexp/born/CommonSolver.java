package regexp.born;

import regexp.MetaString;

public class CommonSolver extends AbstractSolver {
    private String ch;

    CommonSolver(String ch) {
        this.ch = ch;
    }

    @Override
    public boolean solve(MetaString ms) {
        if (ms.notEnd() && ms.cur().equals(ch)) {
            ms.incr();
            return true;
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        if (solve(ms)) {
            boolean result = super.solveAndNext(next(), ms);
            ms.decr();
            return result;
        }
        return false;
    }
}
