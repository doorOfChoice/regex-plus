package regexp.born.analyzers;

import regexp.born.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;
import regexp.born.solvers.DotSolver;

public class DotAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        return mp.notEnd() && mp.cur().equals(".");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp, CoreSolver r) {
        AbstractSolver solver = new DotSolver();
        mp.incr();
        return solver;
    }
}
