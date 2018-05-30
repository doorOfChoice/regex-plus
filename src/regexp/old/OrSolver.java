package regexp.old;

import regexp.MetaString;

public class OrSolver extends AbstractSolver {
    private AbstractSolver head;
    private AbstractSolver tail;

    public OrSolver() {
    }

    @Override
    public void add(AbstractSolver solver) {
        if (tail == null) {
            head = tail = solver;
        } else {
            tail.next = solver;
            solver.prev = tail;
            tail = solver;
        }
    }

    @Override
    public AbstractSolver pop() {
        if (tail == null)
            return null;
        AbstractSolver popNode = tail;
        tail = tail.prev;
        popNode.prev = null;
        if (tail != null)
            tail.next = null;
        else
            head = null;
        return popNode;
    }

    @Override
    public AbstractSolver peek() {
        return tail;
    }

    @Override
    public boolean solve(MetaString ms) {
        AbstractSolver node = head;
        while (node != null) {
            if (!node.solve(ms)) {
                ms.back();
                return false;
            }
            AbstractSolver j = node.tryJump();
            if (j != null && j != node) {
                if (j != tail) {
                    jump = j;
                }
                return true;
            }
            node = node.next;
        }
        ms.update();
        return true;
    }

    @Override
    public String toString() {
        AbstractSolver node = head;
        StringBuilder str = new StringBuilder();
        while (node != null) {
            str.append(node).append("|||");
            node = node.next;
        }
        return str.toString();
    }
}
