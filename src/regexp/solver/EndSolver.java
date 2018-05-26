package regexp.solver;

public class EndSolver extends AbstractSolver {
    public EndSolver(){}
    @Override
    public boolean solve(MetaCommon ms) {
        return !ms.notEnd();
    }
}
