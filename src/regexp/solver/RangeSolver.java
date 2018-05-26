package regexp.solver;

/**
 * 范围解析器
 * 用于匹配类似a-zA-Z类型
 */
public class RangeSolver extends AbstractSolver {
    private String min;
    private String max;

    public RangeSolver(String min, String max) {
        this.min = min ;
        this.max = max ;
    }

    @Override
    public boolean solve(MetaCommon ms) {
        boolean result = ms.notEnd() && min.compareTo(ms.cur()) <= 0 && max.compareTo(ms.cur()) >= 0;
        if (result) ms.incr();
        return result;
    }
}
