package regexp.solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CountSolver extends AbstractSolver {
    private AbstractSolver solver;
    //min为-1表示匹配固定个数为max的字符串
    private int min = -1;
    //max为-1表示无匹配上限
    private int max = -1;

    private boolean greedy = true;

    static CountSolver produceStar(AbstractSolver solver) {
        return new CountSolver(solver, 0, -1);
    }

    static CountSolver producePlus(AbstractSolver solver) {
        return new CountSolver(solver, 1, -1);
    }

    static CountSolver produceQuestion(AbstractSolver solver) {
        return new CountSolver(solver, 0, 1);
    }

    static CountSolver produceFixed(AbstractSolver solver, int count) {
        return new CountSolver(solver, -1, count);
    }

    static CountSolver produce(AbstractSolver solver, int min, int max) {
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

    void setGreedy(boolean greedy) {
        this.greedy = greedy;
    }


    @Override
    public boolean solve(MetaCommon ms) {
        ms.giSave();
        try {
            if (greedy) {
                if (!solveGreedy(ms))
                    return false;
                List<AbstractSolver> solvers = new ArrayList<>();
                AbstractSolver crossNext = getCrossNext(this);
                if (crossNext == null)
                    return true;
                do {
                    crossNext.hasExtraStep = true;
                    solvers.add(crossNext);
                } while ((crossNext = getCrossNext(crossNext)) != null);
                for (int i = ms.i(); i >= ms.gi(); --i) {
                    ms.i(i);
                    if (track(ms, solvers)) {
                        return true;
                    }
                }
            }
            return false;
        } finally {
            ms.giRestore();
        }

    }

    private boolean track(MetaCommon ms, List<AbstractSolver> solvers) {
        for (AbstractSolver s : solvers) {
            boolean result = s.solve(ms);
            //某个元素如果也是CountResolver且成功，则不再判断后面的Solver，因为子CountResolver已经判断过
            if (result && (s.isCount() || s.isTuple()))
                return true;
            //如果有一个出错，则返回false
            else if(!result) {
                return false;
            }
        }
        return true;
    }

    private AbstractSolver getCrossNext(AbstractSolver solver) {
        if (solver.next != null)
            return solver.next;
        AbstractSolver p = solver.parent;
        while (p != null && p.next == null) p = p.parent;
        if (p == null)
            return null;
        return p.next;
    }

    private boolean solveGreedy(MetaCommon ms) {
        if (min == -1) {
            for (int i = 0; i < max && ms.notEnd(); ++i) {
                if (!solver.solve(ms))
                    return false;
            }
        } else if (min > -1 && max != -1) {
            int count = 0;
            while (ms.notEnd() && solver.solve(ms)) {
                ++count;
                if (count > max)
                    return false;
            }
            return count >= min;
        } else if (min > -1) {
            int count = 0;
            while (ms.notEnd() && solver.solve(ms)) {
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
