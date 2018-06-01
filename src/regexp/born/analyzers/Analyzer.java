package regexp.born.analyzers;

import regexp.born.MetaPattern;
import regexp.born.solvers.AbstractSolver;
import regexp.born.solvers.CoreSolver;

/**
 * 分析器用来灵活的定义属于自己的正则表达式
 */
public interface Analyzer  {
    /**
     * 自定义条件
     * @param mp 正则单词解析器
     * @return 是否满足条件
     */
    boolean satisfy(MetaPattern mp);

    /**
     * 满足条件后返回的解析器
     * @param mp 正则单词解析器
     * @param r 正则栈, 包含当前链中的已经生成的Solver
     * @return 自定义解析器
     */
    AbstractSolver gain(MetaPattern mp, CoreSolver r);
}
