package regexp.old;

import regexp.MetaString;

import java.util.ArrayList;
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
    public boolean solve(MetaString ms) {
        ms.giSave();
        try {
            if (greedy) {
                return solveGreedy(ms);
            }
            System.out.println(greedy);
            return solveUnGreedy(ms);
        } finally {
            ms.giRestore();
        }
    }

    /**
     * 贪婪模式中判断solver是否满足匹配次数
     * @param ms
     * @return
     */
    private boolean matchByGreedy(MetaString ms) {
        if (min == -1) {
            for (int i = 0; i < max && ms.notEnd(); ++i) {
                if (!solver.solve(ms))
                    return false;
            }
        } else {
            int count = 0;
            while ((max == -1 || count < max) && ms.notEnd() && solver.solve(ms)) {
                ++count;
            }
            return count >= min;
        }
        return true;
    }

    /**
     * 贪婪模式 匹配次数成功后开始验证本次匹配是否能让后面全对，否则回溯
     * @param ms
     * @return
     */
    private boolean solveGreedy(MetaString ms) {
        if (!matchByGreedy(ms))
            return false;
        if (min == -1)
            return true;
        List<AbstractSolver> solvers = getAllCrossNext(this);
        if (solvers.isEmpty())
            return true;
        int down = ms.gi() + min;
        //验证后面的所以Solver是否都能解析
        for (int i = ms.i(); i >= down; --i) {
            ms.i(i);
            if (track(ms, solvers)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 非贪婪模式 匹配并且回溯
     * @param ms
     * @return
     */
    private boolean solveUnGreedy(MetaString ms) {
        List<AbstractSolver> solvers = getAllCrossNext(this);
        if (min == -1) {
            for (int i = 0; i < max && ms.notEnd(); ++i) {
                if (!solver.solve(ms)) {
                    return false;
                }
            }
            return true;
        }
        //max从0~无穷
        for (int i = 0; i < min && ms.notEnd(); ++i) {
            if (solver.solve(ms))
                return false;
        }
        int index = min;
        while ((max == -1 ||index < max) && ms.notEnd()) {
            ms.giSave();
            if (track(ms, solvers)) {
                ms.giRestore();
                return true;
            }
            ms.i(ms.gi());
            ms.giRestore();
            if (!solver.solve(ms)) {
                return false;
            }
            ++index;
        }
        return true;

    }

    /**
     * 执行一次回溯操作
     * 执行成功则设置jump值，以便Or进行跳转
     * @param ms
     * @param solvers
     * @return
     */
    private boolean track(MetaString ms, List<AbstractSolver> solvers) {
        for (AbstractSolver s : solvers) {
            boolean result = s.solve(ms);
            if (!result)
                return false;
            //某个元素如果也是CountResolver且成功，则不再判断后面的Solver，因为子CountResolver已经判断过
            if (s.isCount()) {
                jump = s.tryJump();
                return true;
            } else if (s.isTuple()) {
                //如果Tuple里面也包含CountSolver
                if (s.jump != s) {
                    jump = s.tryJump();
                    return true;
                }
            }
        }
        jump = solvers.size() != 0 ? solvers.get(solvers.size() - 1) : null;
        return true;
    }

    /**
     * 下一个忽略小括号后获取的节点
     * 例如(1(3))4, 3的下一个忽略右小括号的节点是4，忽略了))
     * @param solver
     * @return
     */
    private AbstractSolver getCrossNext(AbstractSolver solver) {
        if (solver.next != null)
            return solver.next;
        AbstractSolver p = solver.parent;
        while (p != null && p.next == null) p = p.parent;
        if (p == null)
            return null;
        return p.next;
    }

    /**
     * 获取所有的忽略小括号后获取的节点
     * @param solver
     * @return
     */
    private List<AbstractSolver> getAllCrossNext(AbstractSolver solver) {
        List<AbstractSolver> solvers = new ArrayList<>();
        AbstractSolver crossNext = getCrossNext(this);
        if (crossNext == null)
            return solvers;
        do {
            solvers.add(crossNext);
        } while ((crossNext = getCrossNext(crossNext)) != null);
        return solvers;
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
