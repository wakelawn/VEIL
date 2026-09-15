package main.java.com.veil.ast;

import main.java.com.veil.lexer.TokenType;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final TokenType operator;
    private final Expression right;

    public BinaryExpression(
            Expression left,
            TokenType operator,
            Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public TokenType getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "("
                + left
                + " "
                + operator
                + " "
                + right
                + ")";
    }
}