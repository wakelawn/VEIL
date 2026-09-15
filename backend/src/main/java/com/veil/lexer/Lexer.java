package main.java.com.veil.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position;

    public List<Token> tokenize(String input) {
        this.input = input;
        this.position = 0;

        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {

            char current = input.charAt(position);

            // ignoring whitespace
            if (Character.isWhitespace(current)) {
                position++;
                continue;
            }
            // indentifiers and SQL keywords
            if (Character.isLetter(current)) {
                tokens.add(readWord());// readword() will be defined later
                continue;
            }

            // numbers
            if (Character.isDigit(current)) {
                tokens.add(readDigit());// readDigit()vwill be deined later
                continue;
            }

            // single char token

            switch (current) {
                case '=':
                    tokens.add(new Token(TokenType.EQUALS, "=", position));
                    position++;
                    break;

                case '>':
                    tokens.add(new Token(TokenType.GREATER_THAN, ">", position));
                    position++;
                    break;

                case '<':
                    tokens.add(new Token(TokenType.LESS_THAN, "<", position));
                    position++;
                    break;

                case ',':
                    tokens.add(new Token(TokenType.COMMA, ",", position));
                    position++;
                    break;

                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, ";", position));
                    position++;
                    break;

                default:
                    throw new RuntimeException(
                            "Unexpected character '" +
                                    current +
                                    "' at position " +
                                    position);
            }

        }

        return tokens;
    }

    private Token readDigit() {
        int start = position;

        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position++;
        }

        String number = input.substring(start, position);

        return new Token(TokenType.NUMBER, number, start);

    }

    private Token readWord() {
        int start = position;
        while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) {
            position++;
        }
        String word = input.substring(start, position);
        return new Token(TokenType.IDENTIFIER, word, ~start);
    }
}