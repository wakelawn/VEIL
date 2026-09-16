package com.veil.lexer;

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

    // keyword
    SELECT,
    FROM,
    WHERE,

    IDENTIFIER,
    NUMBER,
    STRING,

    // conditional
    GREATER_THAN,
    EQUALS,
    LESS_THAN,
    COMMA,
    SEMICOLON,

    // logicals
    AND,
    OR,
    DOT,

    // arithmatic
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,

    // aggregrate function
    GROUP,
    BY,
    ORDER,
    ASC,
    DESC,

    // joints
    JOIN,
    ON,
    LEFT,
    RIGHT,
    FULL,
    INNER,

    // end of file
    EOF

}
