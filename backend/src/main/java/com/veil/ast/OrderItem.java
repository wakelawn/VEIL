package main.java.com.veil.ast;

public class OrderItem implements ASTNode {

    private final Expression expression;
    private final boolean ascending;

    public OrderItem(Expression expression, boolean ascending) {
        this.expression = expression;
        this.ascending = ascending;//storing as bool for cleaner code
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean isAscending() {
        return ascending;
    }

    @Override
    public String toString() {
        return expression + (ascending ? " ASC" : " DESC");
    }
}