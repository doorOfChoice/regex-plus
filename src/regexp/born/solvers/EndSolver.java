package regexp.born.solvers;

import regexp.MetaString;

public class EndSolver extends AbstractSolver {
    @Override
    public boolean solve(MetaString ms) {
        return !ms.notEnd();
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return solve(ms) && super.solveAndNext(next(), ms);
    }
}
