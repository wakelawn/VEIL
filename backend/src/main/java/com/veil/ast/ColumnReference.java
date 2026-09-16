package com.veil.ast;

public class ColumnReference extends Expression{
    private final String tableAlias;
    private final String columnName;

    public ColumnReference(String tableAlias, String columnName){
        this.tableAlias = tableAlias;
        this.columnName = columnName;
    }

    public String getTablealias (){
        return tableAlias;
    }

    public String getColumnname(){
        return columnName;
    }

    @Override 
    public String toString() {
        if(tableAlias != null){
            return tableAlias + "." + columnName;
        }

        return columnName;
    }
}