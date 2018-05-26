package regexp.solver;

public class EndSolver extends AbstractSolver {
    public EndSolver(){}
    public EndSolver(AbstractSolver parent){
        this.parent = parent;
    }
    @Override
    public boolean solve(MetaCommon ms) {
        return !ms.notEnd();
    }
}
