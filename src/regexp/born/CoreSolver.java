package regexp.born;

import regexp.MetaString;

import java.util.LinkedList;
import java.util.List;

public class CoreSolver extends AbstractSolver {
    private List<SolverList> solverLists = new LinkedList<>();

    public CoreSolver() {
        addOr();
    }

    class SolverList {
        AbstractSolver head;
        AbstractSolver tail;

        void add(AbstractSolver solver) {
            if (tail == null) {
                head = tail = solver;
            } else {
                tail.next = solver;
                solver.prev = tail;
                tail = solver;
            }
        }

        AbstractSolver pop() {
            if (tail == null)
                return null;
            AbstractSolver popNode = tail;
            tail = tail.prev;
            popNode.prev = null;
            if (tail != null)
                tail.next = null;
            else
                head = null;
            return popNode;
        }


        AbstractSolver peek() {
            return tail;
        }

    }


    public void add(AbstractSolver solver) {
        solverLists.get(solverLists.size() - 1).add(solver);
    }

    public AbstractSolver pop() {
        return solverLists.get(solverLists.size() - 1).pop();
    }

    public AbstractSolver peek() {
        return solverLists.get(solverLists.size() - 1).peek();
    }

    public void addOr() {
        solverLists.add(new SolverList());
    }

    public boolean solve(MetaString ms) {
        for (SolverList solverList : solverLists) {
            if (solverList.head != null) {
                if (super.solveAndNext(solverList.head, ms)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return solve(ms);
    }
}
