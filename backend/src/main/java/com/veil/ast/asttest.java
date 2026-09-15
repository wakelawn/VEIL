package main.java.com.veil.ast;

import main.java.com.veil.ast.*;
import main.java.com.veil.lexer.TokenType;

import java.util.List;

public class asttest {

    public static void main(String[] args) {

        // SELECT name, age
        List<Expression> columns = List.of(
                new ColumnReference(null, "name"),
                new ColumnReference(null, "age"));

        // FROM users
        TableReference from = new TableReference("users", null);

        // WHERE age > 18
        Expression condition = new BinaryExpression(
                new ColumnReference(null, "age"),
                TokenType.GREATER_THAN,
                new LiteralExpression(18));

        WhereClause where = new WhereClause(condition);

        // GROUP BY age
        GroupByClause groupBy = new GroupByClause(
                List.of(
                        new ColumnReference(null, "age")));

        // ORDER BY age DESC
        OrderItem orderItem = new OrderItem(
                new ColumnReference(null, "age"),
                false);

        OrderByClause orderBy = new OrderByClause(
                List.of(orderItem));

        // Build SELECT statement
        SelectStatement select = new SelectStatement(
                columns,
                from,
                List.of(),
                where,
                groupBy,
                orderBy);

        System.out.println("AST created successfully!");
        System.out.println(select);
    }
}