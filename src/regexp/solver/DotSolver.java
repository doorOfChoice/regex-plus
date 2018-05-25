package regexp.solver;

public class DotSolver extends AbstractSolver {

    @Override
    public boolean solve(MetaCommon ms) {
        boolean result = ms.notEnd();
        if (result) ms.incr();
        return result;
    }
}
