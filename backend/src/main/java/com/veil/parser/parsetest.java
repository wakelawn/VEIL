
package com.veil.parser;

import com.veil.ast.SelectStatement;
import com.veil.lexer.Lexer;
import com.veil.lexer.Token;
import com.veil.parser.Parser;

import java.util.List;

public class parsetest {

    public static void main(String[] args) {

        String sql1 = "SELECT raj, age FROM users;";// test 1
        String sql2 = "SELECT raj, age FROM users WHERE age > 18;";// test 2
        String sql3 = "SELECT raj, age FROM users WHERE age = 18;";// test 3
        String sql4 = "SELECT raj, age FROM users WHERE age < 18;";// test 4
        String sql5 = "SELECT name FROM users u;";// test 5
        String sql6 = "SELECT u.name FROM users u;";// test 6
        String sql7 = "SELECT department FROM employees GROUP BY department;";// test 7
        String sql8 = "SELECT department, location " + "FROM employees " + "GROUP BY department, location;";// test 8
        String sql9 = "SELECT name, salary FROM employees ORDER BY salary DESC;";// test9
        String sql10 = "SELECT name, salary FROM employees ORDER BY salary ASC;";// test10
        String sql11 = "SELECT name, salary FROM employees ORDER BY salary ASC;";// test 11
        String sql12 = "SELECT u.name, o.total " + "FROM users u " + "JOIN orders o " + "ON u.id = o.user_id;";//test 12

        Lexer lexer = new Lexer();

        List<Token> tokens = lexer.tokenize(sql12);

        Parser parser = new Parser(tokens);

        SelectStatement statement = (SelectStatement) parser.parse();

        System.out.println("yayyyyyyyyyyyyyyy parser is working bruh!");

        // System.out.println(statement.getOrderBy());
        System.out.println(statement);

        // System.out.println(statement.getFrom().getTableName());
        // System.out.println(statement.getFrom().getAlias());
    }
}