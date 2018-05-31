package regexp.born.solvers;

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

    public AbstractSolver next() {
        AbstractSolver n = this;
        while (n != null && n.next == null) n = n.parent;
        return n == null ? null : n.next;
    }

    public AbstractSolver directNext() {
        return next;
    }

    public AbstractSolver prev() {
        return prev;
    }

    public AbstractSolver parent() {
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

    /**
     * 解析对应solver，并且设置分组max下标
     * @param solver
     * @param ms
     * @return
     */
    protected boolean solve(AbstractSolver solver, MetaString ms) {
        setParentMaxRange(ms, ms.i());
        return  solver == null ||solver.solve(ms);
    }
    /**
     * 解析对应solver并且递归解析，并且设置分组max下标
     * @param solver
     * @param ms
     * @return
     */
    protected boolean solveAndNext(AbstractSolver solver, MetaString ms) {
        setParentMaxRange(ms, ms.i());
        return solver == null || solver.solveAndNext(ms);
    }

    /**
     * 设置所有父级的max下标
     * @param ms
     * @param max
     */
    private void setParentMaxRange(MetaString ms, int max) {
        AbstractSolver node = this;
        while (node != null) {
            if (node.group != -1)
                ms.setRangesMax(node.group, max);
            node = node.parent;
        }
    }
}
