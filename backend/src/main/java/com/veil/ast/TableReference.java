package com.veil.ast;

public class TableReference implements ASTNode {

    private final String tablename;
    private final String alias;

    public TableReference(String tablename, String alias) {
        this.tablename = tablename;
        this.alias = alias;
    }

    public String getTableName() {
        return tablename;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        if (alias != null) {
            return tablename + " " + alias;
        }

        return tablename;
    }
}