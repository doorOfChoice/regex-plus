package regexp.solver;

public class CountSolver extends AbstractSolver {
    private AbstractSolver solver;
    //min为-1表示匹配固定个数为max的字符串
    private int min = -1;
    //max为-1表示无匹配上限
    private int max = -1;
    private boolean greedy = false;

    public static CountSolver produceStar(AbstractSolver solver) {
        return new CountSolver(solver, 0, -1);
    }

    public static CountSolver producePlus(AbstractSolver solver) {
        return new CountSolver(solver, 1, -1);
    }

    public static CountSolver produceQuestion(AbstractSolver solver) {
        return new CountSolver(solver, 0, 1);
    }

    public static CountSolver produceFixed(AbstractSolver solver, int count) {
        return new CountSolver(solver, -1, count);
    }

    public static CountSolver produce(AbstractSolver solver, int min, int max) {
        return new CountSolver(solver, min, max);
    }

    /**
     * 从min-max范围的匹配, max为-1表示无上限匹配
     *
     * @param solver
     * @param min
     * @param max
     */
    private CountSolver(AbstractSolver solver, int min, int max) {
        this.solver = solver;
        this.min = min;
        this.max = max;
    }

    public void setGreedy(boolean greedy) {
        this.greedy = greedy;
    }

    @Override
    public boolean solve(MetaCommon ms) {
        if (min == -1) {
            for (int i = 0; i < max; ++i) {
                if (!solver.solve(ms))
                    return false;
            }
        } else if (min > -1 && max != -1) {
            int count = 0;
            while (solver.solve(ms)) {
                ++count;
                if (count > max)
                    return false;
            }
            return count >= min;
        } else if (min > -1) {
            int count = 0;
            while (solver.solve(ms)) {
                ++count;
            }
            return count >= min;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CountSolver[value="
                + solver.toString()
                + ", min="
                + min
                + ", max="
                + max
                + "]";
    }
}
