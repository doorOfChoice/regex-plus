package regexp.old;

import regexp.MetaString;

public class DotSolver extends AbstractSolver {
    public DotSolver() {
    }


    @Override
    public boolean solve(MetaString ms) {
        boolean result = ms.notEnd();
        if (result) ms.incr();
        return result;
    }
}
