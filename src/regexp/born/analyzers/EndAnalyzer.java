package regexp.born.analyzers;

import regexp.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;
import regexp.born.solvers.EndSolver;

public class EndAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp, CoreSolver r) {
        return mp.notEnd() && mp.cur().equals("$");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp) {
        mp.incr();
        return new EndSolver();
    }
}