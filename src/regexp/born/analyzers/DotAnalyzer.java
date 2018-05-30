package regexp.born.analyzers;

import regexp.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.DotSolver;

public class DotAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        return mp.notEnd() && mp.cur().equals(".");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp) {
        AbstractSolver solver = new DotSolver();
        mp.incr();
        return solver;
    }
}
