package main.java.com.veil.ast;

import java.util.List;

public class GroupByClause implements ASTNode {

    private final List<Expression> expressions;//as multiple grouping condition are applicable for sql

    public GroupByClause(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return "GROUP BY " + expressions;
    }
}