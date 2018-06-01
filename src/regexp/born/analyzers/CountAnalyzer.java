package regexp.born.analyzers;

import regexp.born.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;
import regexp.born.solvers.CountSolver;

import java.util.ArrayList;
import java.util.List;

public class CountAnalyzer implements Analyzer {

    @Override
    public boolean satisfy(MetaPattern mp) {
        if (!mp.notEnd())
            return false;
        String ch = mp.cur();
        return ch.equals("*") || ch.equals("?") || ch.equals("{") || ch.equals("+");
    }

    @Override
    public AbstractSolver gain(MetaPattern mp, CoreSolver r) {
        AbstractSolver prev = r.peek();
        String cur = mp.cur();
        if (prev == null)
            return null;
        try {
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
        } finally {
            mp.incr();
        }

    }
}
