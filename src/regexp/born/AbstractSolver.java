package regexp.born;

import regexp.MetaString;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {
    protected AbstractSolver prev;
    protected AbstractSolver next;

    public AbstractSolver() {
    }

    public boolean solve(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        return solver.solve(ms);
    }

    public boolean solveAndNext(AbstractSolver solver, MetaString ms) {
        if (solver == null)
            return true;
        return solver.solveAndNext(ms);
    }

}
