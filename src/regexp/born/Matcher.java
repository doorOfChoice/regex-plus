package regexp.born;

import regexp.MetaPattern;
import regexp.MetaString;
import regexp.born.analyzers.*;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;

import java.util.ArrayList;
import java.util.List;

public class Matcher {
    private CoreSolver root = new CoreSolver();
    private List<Analyzer> analyzers = new ArrayList<>();
    private int groupSize = 0;

    public Matcher(String pattern) {
        initAnalyzer();
        analyzePattern(new MetaPattern(pattern));
    }


    public boolean match(String str) {
        MetaString ms = new MetaString(str);
        ms.buildRanges(groupSize);
        AbstractSolver solver = root;
        return solver.solveAndNext(ms);
    }

    public List<String> matchAndGetGroup(String str) {
        MetaString ms = new MetaString(str);
        ms.buildRanges(groupSize);
        AbstractSolver solver = root;
        if (solver.solveAndNext(ms)) {
            return ms.getRangesString();
        }
        return null;
    }

    public void register(Analyzer analyzer) {
        analyzers.add(analyzer);
    }

    private void analyzePattern(MetaPattern mp) {
        AbstractSolver t;
        CoreSolver r = root;
        while (mp.notEnd()) {
            String ch = mp.cur();
            if (ch.equals("(")) {
                CoreSolver coreSolver = new CoreSolver();
                coreSolver.setParent(r);
                coreSolver.setGroup(groupSize++);
                r.add(coreSolver);
                r = coreSolver;
                mp.incr();
            } else if (ch.equals(")")) {
                r = (CoreSolver) r.parent();
                mp.incr();
            } else if (ch.equals("|")) {
                r.addOr();
                mp.incr();
            } else {
                for (int i = analyzers.size() - 1; i >= 0; --i) {
                    if (analyzers.get(i).satisfy(mp)) {
                        r.add(analyzers.get(i).gain(mp, r));
                        break;
                    }
                }
            }
        }
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
}
