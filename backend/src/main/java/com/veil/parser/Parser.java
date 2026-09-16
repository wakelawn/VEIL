package com.veil.parser;

import com.veil.ast.Statement;
import com.veil.lexer.Token;
import com.veil.lexer.TokenType;

import com.veil.ast.ColumnReference;
import com.veil.ast.Expression;
import com.veil.ast.SelectStatement;
import com.veil.ast.TableReference;

import com.veil.ast.LiteralExpression;
import com.veil.ast.BinaryExpression;
import com.veil.ast.WhereClause;

import com.veil.ast.GroupByClause;
import com.veil.ast.OrderByClause;
import com.veil.ast.OrderItem;
import com.veil.ast.JoinClause;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public SelectStatement parse() {

        if (current().getType() != TokenType.SELECT) {
            throw new ParseException(
                    "Expected SELECT but found "
                            + current().getType());
        }

        SelectStatement statement = parseSelect();

        // Semicolon is optional
        if (current().getType() == TokenType.SEMICOLON) {
            consume(TokenType.SEMICOLON);
        }

        // Nothing should remain after the query
        if (current().getType() != TokenType.EOF) {
            throw new ParseException(
                    "Unexpected token after query: "
                            + current().getValue());
        }

        return statement;
    }

    // main query formation function
    private SelectStatement parseSelect() {

        consume(TokenType.SELECT);

        List<Expression> columns = parseColumns();

        consume(TokenType.FROM);

        TableReference from = parseTable();

        List<JoinClause> joins = new ArrayList<>();

        while (isJoinStart()) {
            joins.add(parseJoin());
        }

        WhereClause where = null;

        if (current().getType() == TokenType.WHERE) {
            where = parseWhere();
        }

        GroupByClause groupBy = null;

        if (current().getType() == TokenType.GROUP) {
            groupBy = parseGroupBy();
        }

        OrderByClause orderBy = null;

        if (current().getType() == TokenType.ORDER) {
            orderBy = parseOrderBy();
        }

        return new SelectStatement(
                columns,
                from,
                joins,
                where,
                groupBy,
                orderBy);
    }

    private Token current() {
        return tokens.get(position);
    }

    // Will be used to parse the columns
    private List<Expression> parseColumns() {

        List<Expression> columns = new ArrayList<>();

        columns.add(parseExpression());

        while (current().getType() == TokenType.COMMA) {

            consume(TokenType.COMMA);

            columns.add(parseExpression());
        }

        return columns;
    }

    // will be used to parse tables
    private TableReference parseTable() {

        Token tableToken = consume(TokenType.IDENTIFIER);

        String alias = null;

        if (current().getType() == TokenType.IDENTIFIER) {
            alias = consume(TokenType.IDENTIFIER).getValue();
        }

        return new TableReference(
                tableToken.getValue(),
                alias);
    }

    // return the expected token and its type
    private Token consume(TokenType expected) {

        Token token = current();

        if (token.getType() != expected) {
            throw new ParseException(
                    "Expected " + expected
                            + " but found "
                            + token.getType());
        }

        position++;

        return token;
    }

    // so that numbers can be parsed during sql query
    private Expression parseExpression() {

        Expression left = parsePrimary();

        TokenType operator = current().getType();

        if (operator == TokenType.EQUALS
                || operator == TokenType.GREATER_THAN
                || operator == TokenType.LESS_THAN) {

            consume(operator);

            Expression right = parsePrimary();

            return new BinaryExpression(
                    left,
                    operator,
                    right);
        }

        return left;
    }

    private Expression parsePrimary() {

        Token token = current();

        if (token.getType() == TokenType.IDENTIFIER) {

            String firstPart = consume(TokenType.IDENTIFIER).getValue();

            if (current().getType() == TokenType.DOT) {

                consume(TokenType.DOT);

                String columnName = consume(TokenType.IDENTIFIER).getValue();

                return new ColumnReference(
                        firstPart,
                        columnName);
            }

            return new ColumnReference(
                    null,
                    firstPart);
        }

        if (token.getType() == TokenType.NUMBER) {

            consume(TokenType.NUMBER);

            return new LiteralExpression(
                    Integer.parseInt(token.getValue()));
        }

        throw new ParseException(
                "Expected expression but found "
                        + token.getType());
    }

    // for the where clause
    private WhereClause parseWhere() {

        consume(TokenType.WHERE);

        Expression condition = parseExpression();

        return new WhereClause(condition);
    }

    // for the group by clause
    private GroupByClause parseGroupBy() {

        consume(TokenType.GROUP);

        consume(TokenType.BY);

        List<Expression> expressions = new ArrayList<>();

        expressions.add(parseExpression());

        while (current().getType() == TokenType.COMMA) {

            consume(TokenType.COMMA);

            expressions.add(parseExpression());
        }

        return new GroupByClause(expressions);
    }

    // for order by
    private OrderByClause parseOrderBy() {

        consume(TokenType.ORDER);

        consume(TokenType.BY);

        List<OrderItem> items = new ArrayList<>();

        items.add(parseOrderItem());

        while (current().getType() == TokenType.COMMA) {

            consume(TokenType.COMMA);

            items.add(parseOrderItem());
        }

        return new OrderByClause(items);
    }

    // for the items in order by
    private OrderItem parseOrderItem() {

        Expression expression = parseExpression();

        boolean ascending = true;

        if (current().getType() == TokenType.ASC) {

            consume(TokenType.ASC);

        } else if (current().getType() == TokenType.DESC) {

            consume(TokenType.DESC);

            ascending = false;
        }

        return new OrderItem(
                expression,
                ascending);
    }

    // to check joins
    private boolean isJoinStart() {

        TokenType type = current().getType();

        return type == TokenType.JOIN
                || type == TokenType.INNER
                || type == TokenType.LEFT
                || type == TokenType.RIGHT
                || type == TokenType.FULL;
    }

    // for JOINS
    private JoinClause parseJoin() {

        JoinClause.JoinType joinType = JoinClause.JoinType.INNER;

        if (current().getType() == TokenType.LEFT) {

            consume(TokenType.LEFT);
            joinType = JoinClause.JoinType.LEFT;

        } else if (current().getType() == TokenType.RIGHT) {

            consume(TokenType.RIGHT);
            joinType = JoinClause.JoinType.RIGHT;

        } else if (current().getType() == TokenType.FULL) {

            consume(TokenType.FULL);
            joinType = JoinClause.JoinType.FULL;

        } else if (current().getType() == TokenType.INNER) {

            consume(TokenType.INNER);
            joinType = JoinClause.JoinType.INNER;
        }

        consume(TokenType.JOIN);

        TableReference table = parseTable();

        consume(TokenType.ON);

        Expression condition = parseExpression();

        return new JoinClause(
                joinType,
                table,
                condition);
    }

}