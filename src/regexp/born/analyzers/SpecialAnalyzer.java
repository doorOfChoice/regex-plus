package regexp.born.analyzers;

import regexp.born.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;
import regexp.born.solvers.SpecialSolver;

public class SpecialAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        return SpecialSolver.isSpecialString(mp.cur());
    }

    @Override
    public AbstractSolver gain(MetaPattern mp, CoreSolver r) {
        AbstractSolver solver = new SpecialSolver(mp.cur());
        mp.incr();
        return solver;
    }
}
