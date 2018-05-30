package regexp.old;

import regexp.MetaString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TupleSolver extends AbstractSolver implements Tupler {
    private List<AbstractSolver> solvers;
    private LinkedList<String> groups;
    private TupleSolver parent;

    public TupleSolver() {
        this.solvers = new ArrayList<>();
        this.groups = new LinkedList<>();
        addOr();
    }

    public TupleSolver getParent() {
        return parent;
    }

    public void setParent(TupleSolver parent) {
        this.parent = parent;
    }

    @Override
    public boolean solve(MetaString ms) {
        //防止每次back都回到0位置，需要每次进入Tuple都更新一次di
        //之前的备份值
        ms.diSave();
        //是否有成功的Or语句
        boolean success = false;

        for (AbstractSolver solver : solvers) {
            if (solver.solve(ms)) {
                success = true;
                if (solver instanceof Tupler)
                    groups.addAll(((Tupler) solver).array());
                groups.addFirst(ms.sDiToI());
                jump = solver.tryJump();
                //如果返回为空，则把自己返回, 因为在CountSolver中解析会提前预判TupleSolver
                if(jump == null)
                    jump = this;
                break;
            }
        }
        //恢复备份上下文
        ms.diRestore();
        return success;
    }

    @Override
    public void add(AbstractSolver solver) {
        solver.parent = this;
        solvers.get(solvers.size() - 1).add(solver);
    }

    @Override
    public AbstractSolver pop() {
        return solvers.get(solvers.size() - 1).pop();
    }

    @Override
    public AbstractSolver peek() {
        return solvers.get(solvers.size() - 1).peek();
    }

    @Override
    public List<String> array() {
        return groups;
    }

    @Override
    public String toString() {
        return solvers.toString();
    }

    void addOr() {
        solvers.add(new OrSolver());
    }

}
