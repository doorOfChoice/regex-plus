package regexp.solver;

import java.util.ArrayList;
import java.util.List;

public class Matcher {
    private AbstractSolver root = null;

    public Matcher(String pattern) {
        analyzePattern(new MetaPattern(pattern));
        System.out.println(root);
    }

    public boolean match(String str) {
        MetaCommon ms = new MetaCommon(str);
        AbstractSolver solver = root;
        return solver.solve(ms);
    }

    private void analyzePattern(MetaPattern mp) {
        TupleSolver r = new TupleSolver();
        AbstractSolver t;
        while (mp.notEnd()) {
            if (mp.cur().equals("(")) {
                TupleSolver next = new TupleSolver();
                next.setParent(r);
                r.add(next);
                r = next;
            } else if (mp.cur().equals(")")) {
                r = r.getParent();
            } else if (mp.cur().equals("|")) {
                r.addOr();
            } else if ((t = getCountSolver(mp, r)) != null) {
                r.add(t);
            } else {
                r.add(getSimpleSolver(mp.cur()));
            }
            mp.incr();
        }
        root = r;
    }

    private AbstractSolver getCountSolver(MetaPattern mp, TupleSolver r) {
        AbstractSolver prev = r.peek();
        if (prev == null)
            return null;
        if (mp.cur().equals("*")) {
            return CountSolver.produceStar(r.pop());
        } else if (mp.cur().equals("+")) {
            return CountSolver.producePlus(r.pop());
        } else if (mp.cur().equals("{")) {
            List<Integer> numbers = new ArrayList<>();
            StringBuilder buf = new StringBuilder();
            do {
                mp.incr();
                char ch = mp.cur().charAt(0);
                if (ch == '}' || ch == ',') {
                    numbers.add(new Integer(buf.toString()));
                    buf = new StringBuilder();
                    if (numbers.size() > 2)
                        throw new IllegalArgumentException("Please input a right count");
                }
                if (ch == ',')
                    continue;
                if (ch == '}')
                    break;
                if (!Character.isDigit(ch)) {
                    throw new IllegalArgumentException("Please input a right count");
                }
                buf.append(ch);
            } while (mp.notEnd());
            if (numbers.size() == 0) {
                throw new IllegalArgumentException("Please input a right count");
            } else if (numbers.size() == 1) {
                return CountSolver.produceFixed(r.pop(), numbers.get(0));
            }
            return CountSolver.produce(r.pop(), numbers.get(0), numbers.get(1));
        }

        return null;
    }

    private AbstractSolver getSimpleSolver(String s) {
        if (s.equals("^")) {
            return new BeginSolver();
        } else if (s.equals("$")) {
            return new EndResolver();
        } else if (s.equals(".")) {
            return new DotSolver();
        } else if (CharUtil.isSpecialString(s)) {
            return new SpecialSolver(s);
        }
        return new CommonSolver(s);

    }
}
