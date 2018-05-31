package regexp.born;

import regexp.MetaString;
import regexp.born.solvers.CoreSolver;

import java.util.List;

public class Matcher {
    private CoreSolver root;
    private MetaString ms;
    private int start = 0;

    Matcher(CoreSolver root, MetaString ms) {
        this.root = root;
        this.ms = ms;
    }


    public boolean match() {
        return root.solveAndNext(ms);
    }


    public boolean find() {
        ms.clear();
        do {
            ms.i(start);
        } while (start++ < ms.size() && !root.solveAndNext(ms));
        return start < ms.size();
    }

    public List<String> group() {
        return ms.getRangesString();
    }


}
