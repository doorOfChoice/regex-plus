package regexp.born.solvers;

import regexp.MetaString;

import java.util.ArrayList;
import java.util.List;

/**
 * []解析器
 * 匹配单个字符的 或 语句
 */
public class SquareSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();
    private boolean not = false;

    public SquareSolver() {
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public void add(AbstractSolver solver) {
        solvers.add(solver);
    }

    public int size() {
        return solvers.size();
    }

    @Override
    public boolean solve(MetaString ms) {
        for (Solver solver : solvers) {
            if (solver.solve(ms))
                return !not;
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        if (solve(ms)) {
            boolean result = super.solveAndNext(next(), ms);
            ms.decr();
            return result;
        }
        return false;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }
}
