package regexp.born;

import regexp.MetaString;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {
    private AbstractSolver prev;
    private AbstractSolver next;
    private AbstractSolver parent;
    private int group = -1;

    public AbstractSolver() {
    }

    protected AbstractSolver next() {
        AbstractSolver n = this;
        while (n != null && n.next == null) n = n.parent;
        return n == null ? null : n.next;
    }

    protected AbstractSolver directNext() {
        return next;
    }

    protected AbstractSolver prev() {
        return prev;
    }

    protected AbstractSolver parent() {
        return parent;
    }

    public void setPrev(AbstractSolver prev) {
        this.prev = prev;
    }

    public void setNext(AbstractSolver next) {
        this.next = next;
    }

    public void setParent(CoreSolver parent) {
        this.parent = parent;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    protected boolean solve(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        if (solver.solve(ms)) {
            setParentMaxRange(ms, ms.i());
            return true;
        }
        return false;
    }

    protected boolean solveAndNext(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        setParentMaxRange(ms, ms.i());
        return solver.solveAndNext(ms);
    }


    private void setParentMaxRange(MetaString ms, int max) {
        AbstractSolver node = this;
        while (node != null) {
            if (node.group != -1)
                ms.setRangesMax(node.group, max);
            node = node.parent;
        }
    }
}
