package regexp.solver;

import java.util.ArrayList;
import java.util.List;

public class OrSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();

    public OrSolver() {
    }

    public OrSolver(AbstractSolver parent) {
        super(parent);
    }

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
                //之前进行过贪婪操作并且回溯找到相应值
                if (solver.parent != null
                        && solver.parent.isCount()
                        && ms.isGreedy()
                        && track(solver, ms)) {
                    continue;
                }
                ms.back();
                return false;
            }
        }
        ms.update();
        return true;
    }

    private boolean track(Solver solver, MetaCommon ms) {
        for (int i = ms.i() - 1; i >= ms.gi(); --i) {
            ms.i(i);
            if (solver.solve(ms)) {
                ms.giDel();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }
}
