package regexp.solver;

public class DotSolver extends AbstractSolver {
    public DotSolver() {
    }


    @Override
    public boolean solve(MetaCommon ms) {
        boolean result = ms.notEnd();
        if (result) ms.incr();
        return result;
    }
}
