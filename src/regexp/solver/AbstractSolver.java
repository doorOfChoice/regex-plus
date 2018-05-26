package regexp.solver;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {
    protected AbstractSolver parent;

    public AbstractSolver() {
    }

    public AbstractSolver(AbstractSolver parent) {
        this.parent = parent;
    }

    public void setParent(AbstractSolver parent) {
        this.parent = parent;
    }

    public boolean isSpecial() {
        return this instanceof SpecialSolver;
    }

    public boolean isBegin() {
        return this instanceof BeginSolver;
    }

    public boolean isCommon() {
        return this instanceof CommonSolver;
    }

    public boolean isCount() {
        return this instanceof CountSolver;
    }

    public boolean isDot() {
        return this instanceof DotSolver;
    }

    public boolean isEnd() {
        return this instanceof EndSolver;
    }

    public boolean isOr() {
        return this instanceof OrSolver;
    }

    public boolean isSquare() {
        return this instanceof SquareSolver;
    }

    public boolean isTuple() {
        return this instanceof CommonSolver;
    }

    public boolean isRange() {
        return this instanceof RangeSolver;
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

    public int size() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

}
