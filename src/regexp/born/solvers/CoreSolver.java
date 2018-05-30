package regexp.born.solvers;

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
            if (parent() != null) {
                solver.setParent(CoreSolver.this);
            }
            if (tail == null) {
                head = tail = solver;
            } else {
                tail.setNext(solver);
                solver.setPrev(tail);
                tail = solver;
            }
        }

        AbstractSolver pop() {
            if (tail == null)
                return null;
            AbstractSolver popNode = tail;
            tail = tail.prev();
            popNode.setPrev(null);
            if (tail != null)
                tail.setNext(null);
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
        Label:
        for (SolverList solverList : solverLists) {
            AbstractSolver node = solverList.head;
            //设置当前分组的最小下标
            if (getGroup() != -1)
                ms.setRangesMin(getGroup(), ms.i());
            while (node != null) {
                if (!super.solve(node, ms)) {
                    continue Label;
                }
                node = node.directNext();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        for (SolverList solverList : solverLists) {
            ms.diSave();
            if (getGroup() != -1)
                ms.setRangesMin(getGroup(), ms.i());
            if (super.solveAndNext(solverList.head, ms)) {
                return true;
            }
            ms.diRestore();
        }
        return false;
    }
}
