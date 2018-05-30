package regexp.old;

import regexp.MetaString;

public class EndSolver extends AbstractSolver {
    public EndSolver(){}
    @Override
    public boolean solve(MetaString ms) {
        return !ms.notEnd();
    }
}
