package regexp.solver;

public class DotSolver extends AbstractSolver {
    public DotSolver(){}
    public DotSolver(AbstractSolver parent){
        this.parent = parent;
    }
    @Override
    public boolean solve(MetaCommon ms) {
        boolean result = ms.notEnd();
        if (result) ms.incr();
        return result;
    }
}
