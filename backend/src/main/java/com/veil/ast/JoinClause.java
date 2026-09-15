package main.java.com.veil.ast;

public class JoinClause implements ASTNode {

    public enum JoinType {
        INNER,
        LEFT,
        RIGHT,
        FULL
    }

    private final JoinType joinType;
    private final TableReference table;
    private final Expression condition;

    public JoinClause(
            JoinType joinType,
            TableReference table,
            Expression condition
    ) {
        this.joinType = joinType;
        this.table = table;
        this.condition = condition;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public TableReference getTable() {
        return table;
    }

    public Expression getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return joinType + " JOIN "
                + table
                + " ON "
                + condition;
    }
}