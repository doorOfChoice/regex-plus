package regexp.born;

import regexp.MetaString;

public class EndSolver extends AbstractSolver {
    @Override
    public boolean solve(MetaString ms) {
        return !ms.notEnd();
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return super.solve(next(), ms);
    }
}
