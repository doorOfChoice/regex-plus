package regexp.born.analyzers;

import regexp.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.BeginSolver;
import regexp.born.solvers.CoreSolver;

public class BeginAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp, CoreSolver r) {
        return mp.notEnd() && mp.cur().equals("^");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp) {
        AbstractSolver solver = new BeginSolver();
        mp.incr();
        return solver;
    }
}
