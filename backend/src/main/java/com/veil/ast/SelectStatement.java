package main.java.com.veil.ast;

import java.util.List;

public class SelectStatement extends Statement {

    private final List<Expression> columns;
    private final TableReference from;
    private final List<JoinClause> joins;
    private final WhereClause where;
    private final GroupByClause groupBy;
    private final OrderByClause orderBy;

    public SelectStatement(
            List<Expression> columns,
            TableReference from,
            List<JoinClause> joins,
            WhereClause where,
            GroupByClause groupBy,
            OrderByClause orderBy) {

        this.columns = columns;
        this.from = from;
        this.joins = joins;
        this.where = where;
        this.groupBy = groupBy;
        this.orderBy = orderBy;
    }

    public List<Expression> getColumns() {
        return columns;
    }

    public TableReference getFrom() {
        return from;
    }

    public List<JoinClause> getJoins() {
        return joins;
    }

    public WhereClause getWhere() {
        return where;
    }

    public GroupByClause getGroupBy() {
        return groupBy;
    }

    public OrderByClause getOrderBy() {
        return orderBy;
    }

    @Override
    public String toString() {
        return "SELECT " + columns
                + " FROM " + from
                + (joins != null && !joins.isEmpty() ? " " + joins : "")
                + (where != null ? " " + where : "")
                + (groupBy != null ? " " + groupBy : "")
                + (orderBy != null ? " " + orderBy : "");
    }
}