package regexp.born.solvers;

import regexp.born.MetaString;

public class CommonSolver extends AbstractSolver {
    private String ch;

    public CommonSolver(String ch) {
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
