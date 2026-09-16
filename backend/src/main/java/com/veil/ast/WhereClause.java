package com.veil.ast;

public class WhereClause implements ASTNode {

    private final Expression condition;

    public WhereClause(Expression condition) {
        this.condition = condition;
    }

    public Expression getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "WHERE " + condition;
    }
}