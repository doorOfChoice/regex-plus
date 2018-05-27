package regexp.solver;

import java.util.ArrayList;
import java.util.List;

public class OrSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();

    public OrSolver() {
    }


    @Override
    public void add(AbstractSolver solver) {
        AbstractSolver prevNode = peek();
        if (prevNode != null) prevNode.next = solver;
        solver.prev = prevNode;
        solvers.add(solver);
    }

    @Override
    public AbstractSolver pop() {
        int index = solvers.size() - 1;
        if (index < 0)
            return null;
        AbstractSolver removeNode = solvers.remove(index);
        if (removeNode.prev != null)
            removeNode.prev.next = null;
        return removeNode;
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
            if(solver.hasExtraStep)
                continue;
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
