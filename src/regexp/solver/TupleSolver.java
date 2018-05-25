package regexp.solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TupleSolver extends AbstractSolver implements Tupler {
    private List<AbstractSolver> solvers;
    private LinkedList<String> groups;
    private TupleSolver parent;


    public TupleSolver() {
        this.solvers = new ArrayList<>();
        this.groups = new LinkedList<>();
        addOr();
    }

    public TupleSolver getParent() {
        return parent;
    }

    public void setParent(TupleSolver parent) {
        this.parent = parent;
    }

    @Override
    public boolean solve(MetaCommon ms) {
        //是否有成功的Or语句
        boolean ok = false;
        for (Solver solver : solvers) {
            if (solver.solve(ms)) {
                ok = true;
                if (solver instanceof Tupler)
                    groups.addAll(((Tupler) solver).array());
                groups.addFirst(ms.sDiToI());
                break;
            }
        }
        return ok;
    }

    @Override
    public void add(AbstractSolver solver) {
        solvers.get(solvers.size() - 1).add(solver);
    }

    @Override
    public AbstractSolver pop() {
        return solvers.get(solvers.size() - 1).pop();
    }

    @Override
    public AbstractSolver peek() {
        return solvers.get(solvers.size() - 1).peek();
    }

    public void addOr() {
        solvers.add(new OrSolver());
    }

    @Override
    public List<String> array() {
        return groups;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }
}
