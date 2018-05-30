package regexp.born;

import regexp.MetaString;
import regexp.MetaPattern;

import java.util.ArrayList;
import java.util.List;

public class Matcher {
    private CoreSolver root = new CoreSolver();

    public Matcher(String pattern) {
        analyzePattern(new MetaPattern(pattern));

    }

    public boolean match(String str) {
        MetaString ms = new MetaString(str);
        AbstractSolver solver = root;
        return solver.solve(ms);
    }

    private void analyzePattern(MetaPattern mp) {
        AbstractSolver t;
        CoreSolver r = root;
        while (mp.notEnd()) {
            if (mp.cur().equals("|")) {
                r.addOr();
            } else if ((t = getCountSolver(mp)) != null) {
                r.add(t);
            } else {
                r.add(getSimpleSolver(mp.cur()));
            }
            mp.incr();
        }
    }

    /**
     * 分析包含在[]中的Solver
     *
     * @param mp
     * @return
     */
//    private AbstractSolver getSquareSolver(MetaPattern mp) {
//        SquareSolver squareSolver = new SquareSolver();
//        boolean isRange = false;
//        String prev = null;
//
//        Label:
//        while (true) {
//            //因为是从[开始，所以判断下一个下标是否合法
//            if (!mp.ok())
//                throw new IllegalArgumentException("Cannot find close ]");
//            mp.incr();
//            String t = mp.cur();
//
//            switch (t) {
//                case "-":
//                    isRange = true;
//                    break;
//                case "^":
//                    squareSolver.setNot(true);
//                    break;
//                default:
//                    //前继节点不为空, 说明可以进行单个和范围判断
//                    if (prev != null) {
//                        if (!isRange) {
//                            squareSolver.add(CharUtil.isSpecialString(t) ? new SpecialSolver(prev) : new CommonSolver(prev));
//                            prev = t;
//                        } else {
//                            squareSolver.add(new RangeSolver(prev, t));
//                            prev = null;
//                            isRange = false;
//                        }
//                    } else {
//                        prev = t;
//                    }
//                    //到末尾，跳出
//                    if (t.equals("]")) {
//                        break Label;
//                    }
//                    break;
//            }
//        }
//        if (squareSolver.size() == 0)
//            throw new IllegalArgumentException("U need pass some args into []");
//        return squareSolver;
//    }

    /**
     * 分析出{0,n},*,+,?的数量匹配
     *
     * @param mp
     * @param
     * @return
     */
    private AbstractSolver getCountSolver(MetaPattern mp) {
        CoreSolver r = root;
        AbstractSolver prev = r.peek();
        String cur = mp.cur();
        if (prev == null)
            return null;
        switch (cur) {
            case "*":
                return CountSolver.produceStar(r.pop());
            case "+":
                return CountSolver.producePlus(r.pop());
            case "?":
                //非贪婪
                if (prev instanceof CountSolver) {
                    ((CountSolver) prev).setGreedy(false);
                    return r.pop();
                }
                //贪婪
                return CountSolver.produceQuestion(r.pop());
            case "{":
                List<Integer> numbers = new ArrayList<>();
                StringBuilder buf = new StringBuilder();
                boolean isEndless = false;
                do {
                    mp.incr();
                    char ch = mp.cur().charAt(0);
                    if (ch == '}' || ch == ',') {
                        //逗号说明可能是无上界匹配
                        if (ch == ',')
                            isEndless = true;
                        //缓冲中没有数字则不加入,比如{1,}
                        if (buf.length() != 0)
                            numbers.add(new Integer(buf.toString()));
                        buf = new StringBuilder();
                        if (numbers.size() > 2)
                            throw new IllegalArgumentException("Please input a right count < 2");
                    }
                    if (ch == ',')
                        continue;
                    if (ch == '}')
                        break;
                    //判断{}中的字符是否是数字
                    if (!Character.isDigit(ch)) {
                        throw new IllegalArgumentException("Please input a right count");
                    }
                    //如果有第二个数字说明不是无上界匹配
                    isEndless = false;
                    buf.append(ch);
                } while (mp.notEnd());

                switch (numbers.size()) {
                    case 0:
                        throw new IllegalArgumentException("Please input a right count");
                    case 1:
                        return isEndless ? CountSolver.produce(r.pop(), numbers.get(0), -1) :
                                CountSolver.produceFixed(r.pop(), numbers.get(0));
                    default:
                        CountSolver.produce(r.pop(), numbers.get(0), numbers.get(1));
                }
        }

        return null;
    }

    /**
     * 简单的单个字符匹配
     *
     * @param s
     * @return
     */
    private AbstractSolver getSimpleSolver(String s) {
        if (s.equals("^")) {
            return new BeginSolver();
        } else if (s.equals("$")) {
            return new EndSolver();
        } else if (s.equals(".")) {
            return new DotSolver();
        }
        return new CommonSolver(s);

    }
}
