package regexp.born;

import regexp.MetaString;

public class DotSolver extends AbstractSolver {
    @Override
    public boolean solve(MetaString ms) {
        if (ms.notEnd()) {
            ms.incr();
            return true;
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        if (solve(ms)) {
            boolean result = super.solve(next(), ms);
            ms.decr();
            return result;
        }
        return false;
    }
}