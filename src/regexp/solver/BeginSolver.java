package regexp.solver;

public class BeginSolver extends AbstractSolver {
    public BeginSolver() {
    }

    public BeginSolver(AbstractSolver parent) {
        this.parent = parent;
    }

    @Override
    public boolean solve(MetaCommon ms) {
        return true;
    }
}
