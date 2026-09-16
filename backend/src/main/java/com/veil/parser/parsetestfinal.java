package com.veil.parser;

import com.veil.ast.SelectStatement;
import com.veil.lexer.Lexer;
import com.veil.lexer.Token;
import com.veil.parser.Parser;

import java.util.List;

public class parsetestfinal {

    public static void main(String[] args) {

        String[] queries = {

                // 1. Basic Select
                "SELECT name, age FROM users;",

                // 2. SELECT 
                "SELECT name, 18 FROM users;",

                // 3. WHERE
                "SELECT name FROM users WHERE age > 18;",

                // 4. WHERE with AND
                "SELECT name FROM users " +
                        "WHERE age > 18 AND salary > 50000;",

                // 5.  alias +  column
                "SELECT u.name FROM users u " +
                        "WHERE u.age > 18;",

                // 6. GROUP BY
                "SELECT department FROM employees " +
                        "GROUP BY department;",

                // 7. ORDER BY
                "SELECT name, salary FROM employees " +
                        "ORDER BY salary DESC;",

                // 8. JOIN
                "SELECT u.name, o.total " +
                        "FROM users u " +
                        "JOIN orders o " +
                        "ON u.id = o.user_id;"
        };

        Lexer lexer = new Lexer();

        for (int i = 0; i < queries.length; i++) {

            System.out.println();
            System.out.println("====================================");
            System.out.println("TEST " + (i + 1));
            System.out.println("====================================");

            String sql = queries[i];

            System.out.println("SQL:");
            System.out.println(sql);

            try {

                // Lexing
                List<Token> tokens = lexer.tokenize(sql);

                // Parsing
                Parser parser = new Parser(tokens);

                SelectStatement statement = parser.parse();

                System.out.println("RESULT:");
                System.out.println(statement);

                System.out.println("PASSED");

            } catch (Exception e) {

                System.out.println("FAILED");
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println();
        System.out.println("====================================");
        System.out.println("ALL TESTS FINISHED");
        System.out.println("====================================");
    }
}