package regexp.born.solvers;

import regexp.MetaString;

public class BeginSolver extends AbstractSolver {
    @Override
    public boolean solve(MetaString ms) {
        return true;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return super.solveAndNext(next(), ms);
    }
}
