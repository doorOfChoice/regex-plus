package regexp.born;

import regexp.MetaString;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {
    private AbstractSolver prev;
    private AbstractSolver next;
    private AbstractSolver parent;

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

    boolean solve(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        return solver.solve(ms);
    }

    boolean solveAndNext(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        return solver.solveAndNext(ms);
    }

}
