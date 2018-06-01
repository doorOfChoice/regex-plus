package regexp.born.analyzers;

import regexp.born.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CommonSolver;
import regexp.born.solvers.CoreSolver;

public class CommonAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        return true;
    }

    @Override
    public AbstractSolver gain(MetaPattern mp, CoreSolver r) {
        AbstractSolver solver = new CommonSolver(mp.cur());
        mp.incr();
        return solver;
    }
}
