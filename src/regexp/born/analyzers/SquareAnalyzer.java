package regexp.born.analyzers;

import regexp.CharUtil;
import regexp.MetaPattern;
import regexp.born.solvers.*;

/**
 * [abc],[a-z],[^a] 语句的解析器
 */
public class SquareAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        return mp.notEnd() && mp.cur().equals("[");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp) {
        SquareSolver squareSolver = new SquareSolver();
        boolean isRange = false;
        String prev = null;

        Label:
        while (true) {
            //因为是从[开始，所以判断下一个下标是否合法
            if (!mp.ok())
                throw new IllegalArgumentException("Cannot find close ]");
            mp.incr();
            String t = mp.cur();
            switch (t) {
                case "-":
                    isRange = true;
                    break;
                case "^":
                    squareSolver.setNot(true);
                    break;
                default:
                    //前继节点不为空, 说明可以进行单个和范围判断
                    if (prev != null) {
                        if (!isRange) {
                            squareSolver.add(CharUtil.isSpecialString(t) ? new SpecialSolver(prev) : new CommonSolver(prev));
                            prev = t;
                        } else {
                            squareSolver.add(new RangeSolver(prev, t));
                            prev = null;
                            isRange = false;
                        }
                    } else {
                        prev = t;
                    }
                    //到末尾，跳出
                    if (t.equals("]")) {
                        break Label;
                    }
                    break;
            }
        }
        if (squareSolver.size() == 0)
            throw new IllegalArgumentException("U need pass some args into []");
        mp.incr();
        return squareSolver;
    }
}
