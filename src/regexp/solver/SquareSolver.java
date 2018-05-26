package regexp.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * []解析器
 * 匹配单个字符的 或 语句
 */
public class SquareSolver extends AbstractSolver {
    private List<AbstractSolver> solvers = new ArrayList<>();
    private boolean not = false;

    public void setNot(boolean not) {
        this.not = not;
    }

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
            if (solver.solve(ms))
                return !not;
        }
        //如果不是否定句式说明字符串没有移动，那么进入OrSolver解析下一个Solver就会失败
        //判断否定句式则移动一格下标
        if(not)
            ms.incr();
        return not;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }
}
