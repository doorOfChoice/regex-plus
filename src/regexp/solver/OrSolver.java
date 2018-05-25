package regexp.solver;

import java.util.ArrayList;
import java.util.List;

public class OrSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();


    @Override
    public void add(AbstractSolver solver) {
        solvers.add(solver);
    }

    @Override
    public AbstractSolver pop() {
        int index = solvers.size() - 1;
        if (index < 0)
            return null;
        return solvers.remove(index);
    }

    @Override
    public AbstractSolver peek() {
        int index = solvers.size() - 1;
        if (index < 0)
            return null;
        return solvers.get(index);
    }

    @Override
    public boolean solve(MetaCommon ms) {
        for (AbstractSolver solver : solvers) {
            if (!solver.solve(ms)) {
                ms.back();
                return false;
            }
        }
        ms.update();
        return true;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }
}
