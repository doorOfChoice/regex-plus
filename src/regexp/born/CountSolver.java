package regexp.born;


import regexp.MetaString;

public class CountSolver extends AbstractSolver {
    private AbstractSolver solver;
    private int min = -1;
    private int max = -1;
    private boolean greedy = true;

    void setGreedy(boolean greedy) {
        this.greedy = greedy;
    }

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

    CountSolver(AbstractSolver solver, int min, int max) {
        this.solver = solver;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean solve(MetaString ms) {
        if (min == -1) {
            for (int i = 0; i < max; ++i) {
                if (!super.solve(solver, ms)) {
                    return false;
                }
            }
        }
        int count = 0;
        int i = ms.i();
        int gi = i;
        if (greedy) {
            while (ms.notEnd() && (count < max || max == -1) && super.solve(solver, ms)) {
                i = ms.i();
                ++count;
            }
            if (count >= min) {
                for (int j = i; j >= gi + min; --j) {
                    ms.i(j);
                    if (super.solveAndNext(next, ms)) {
                        return true;
                    }
                }
            }
        } else {
            for (int j = 0; j < min; ++j) {
                if (!super.solve(solver, ms)) {
                    return false;
                }
            }
            int dec = max - min;
            do {
                if (super.solveAndNext(next, ms)) {
                    return true;
                }
                if (!super.solve(solver, ms))
                    return false;
            } while (max == -1 || count++ <= dec);
        }
        return false;
    }

    @Override
    public boolean solveAndNext(MetaString ms) {
        return solve(ms);
    }
}
