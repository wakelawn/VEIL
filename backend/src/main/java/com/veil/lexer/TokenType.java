package main.java.com.veil.lexer;

public enum TokenType {
    // SELECT → SELECT
    // name → IDENTIFIER
    // FROM → FROM
    // users → IDENTIFIER
    // WHERE → WHERE
    // age → IDENTIFIER
    // > → GREATER_THAN
    // 18 → NUMBER
    // ; → SEMICOLON

    SELECT,
    FROM,
    WHERE,

    IDENTIFIER,
    NUMBER,
    STRING,

    GREATER_THAN,
    EQUALS,
    LESS_THAN,
    COMMA,
    SEMICOLON,

    AND,
    OR,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,

}
