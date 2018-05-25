package regexp.solver;

public class EndResolver extends AbstractSolver {
    @Override
    public boolean solve(MetaCommon ms) {
        return !ms.notEnd();
    }
}
