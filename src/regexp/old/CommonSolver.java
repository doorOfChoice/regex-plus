package regexp.old;

import regexp.MetaString;

public class CommonSolver extends AbstractSolver {
    private String value;

    public CommonSolver(String value) {
        this(null, value);
    }

    public CommonSolver(AbstractSolver parent, String value) {
        this.value = value;
    }

    @Override
    public boolean solve(MetaString ms) {
        boolean result = ms.notEnd() && value.equals(ms.cur());
        if (result)
            ms.incr();
        return result;
    }

    @Override
    public String toString() {
        return "CommonSolver[value=" + value + "]";
    }
}