package regexp.solver;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {

    public AbstractSolver() {
    }

    /**
     * 对于像TupleSolver 和 OrSolver 这两种解析器使用, 因为里面存储了Solver列表
     *
     * @param solver
     */
    public void add(AbstractSolver solver) {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

    /**
     * 提供给TupleSolver 和 OrSolver 使用
     *
     * @return
     */
    public AbstractSolver pop() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

    /**
     * 提供给TupleSolver 和 OrSolver 使用
     *
     * @return
     */
    public AbstractSolver peek() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

}
