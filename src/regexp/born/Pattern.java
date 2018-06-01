package regexp.born;

import regexp.born.analyzers.*;
import regexp.born.solvers.CoreSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pattern {
    private CoreSolver root = new CoreSolver();
    private List<Analyzer> analyzers = new ArrayList<>();
    private int groupSize = 0;

    public static Pattern compile(String pattern, Analyzer... ays) {
        return new Pattern(pattern, ays);
    }

    private Pattern(String pattern, Analyzer... ays) {
        initAnalyzer();
        if (ays != null)
            analyzers.addAll(Arrays.asList(ays));
        analyzePattern(new MetaPattern(pattern));
    }


    public Matcher matcher(String string) {
        MetaString ms = new MetaString(string);
        ms.buildRanges(groupSize);
        return new Matcher(root, ms);
    }

    private void initAnalyzer() {
        analyzers.add(new CommonAnalyzer());
        analyzers.add(new SpecialAnalyzer());
        analyzers.add(new DotAnalyzer());
        analyzers.add(new CountAnalyzer());
        analyzers.add(new SquareAnalyzer());
        analyzers.add(new EndAnalyzer());
        analyzers.add(new BeginAnalyzer());
    }

    private void analyzePattern(MetaPattern mp) {
        CoreSolver r = root;
        while (mp.notEnd()) {
            String ch = mp.cur();
            switch (ch) {
                case "(":
                    CoreSolver coreSolver = new CoreSolver();
                    coreSolver.setParent(r);
                    coreSolver.setGroup(groupSize++);
                    r.add(coreSolver);
                    r = coreSolver;
                    mp.incr();
                    break;
                case ")":
                    r = (CoreSolver) r.parent();
                    mp.incr();
                    break;
                case "|":
                    r.addOr();
                    mp.incr();
                    break;
                default:
                    for (int i = analyzers.size() - 1; i >= 0; --i) {
                        if (analyzers.get(i).satisfy(mp)) {
                            r.add(analyzers.get(i).gain(mp, r));
                            break;
                        }
                    }
                    break;
            }
        }
    }
}
