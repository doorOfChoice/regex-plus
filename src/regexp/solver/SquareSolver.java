package regexp.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * []解析器
 * 匹配单个字符的 或 语句
 */
public class SquareSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();

    @Override
    public void add(AbstractSolver solver) {
        solvers.add(solver);
    }

    @Override
    public int size() {
        return solvers.size();
    }

    @Override
    public boolean solve(MetaCommon ms) {
        for (Solver solver : solvers) {
            if (solver.solve(ms)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return solvers.toString();
    }
}
