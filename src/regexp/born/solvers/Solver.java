package regexp.born.solvers;

import regexp.born.MetaString;

/**
 * Solver接口负责匹配正则字符和实际字符
 * 通过实现不同的Solver，让正则整体作为匹配单元，而不是单个字符
 */
public interface Solver {
    boolean solve(MetaString ms);

    boolean solveAndNext(MetaString ms);
}
