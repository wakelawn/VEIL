package com.veil.ast;

import java.util.List;

public class OrderByClause implements ASTNode {

    private final List<OrderItem> items;

    public OrderByClause(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "ORDER BY " + items;
    }
}