package regexp.old;

/**
 * AbstractSolver提供抽象的方法供Solver的具体实现使用
 */
public abstract class AbstractSolver implements Solver {
    protected AbstractSolver prev;
    protected AbstractSolver next;
    protected AbstractSolver parent;
    protected AbstractSolver jump;

    public AbstractSolver() {
    }

    AbstractSolver tryJump() {
        AbstractSolver node = jump;
        jump = null;
        return node;
    }

    boolean isSpecial() {
        return this instanceof SpecialSolver;
    }

    boolean isBegin() {
        return this instanceof BeginSolver;
    }

    boolean isCommon() {
        return this instanceof CommonSolver;
    }

    boolean isCount() {
        return this instanceof CountSolver;
    }

    boolean isDot() {
        return this instanceof DotSolver;
    }

    boolean isEnd() {
        return this instanceof EndSolver;
    }

    boolean isOr() {
        return this instanceof OrSolver;
    }

    boolean isSquare() {
        return this instanceof SquareSolver;
    }

    boolean isTuple() {
        return this instanceof TupleSolver;
    }

    boolean isRange() {
        return this instanceof RangeSolver;
    }

    /**
     * 对于像TupleSolver 和 OrSolver 这两种解析器使用, 因为里面存储了Solver列表
     *
     * @param solver
     */
    void add(AbstractSolver solver) {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

    /**
     * 提供给TupleSolver 和 OrSolver 使用
     *
     * @return
     */
    AbstractSolver pop() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

    /**
     * 提供给TupleSolver 和 OrSolver 使用
     *
     * @return
     */
    AbstractSolver peek() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

    int size() {
        throw new UnsupportedOperationException("This Solver is not contain add method");
    }

}
