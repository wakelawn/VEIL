package com.veil.lexer;

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
            if (Character.isLetter(current) || current == '_') {
                tokens.add(readWord());
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

                case '.':
                    tokens.add(new Token(TokenType.DOT, ".", position));
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
        tokens.add(new Token(TokenType.EOF, "", position));

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
        while (position < input.length()
                && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            position++;
        }
        String word = input.substring(start, position);
        TokenType type = TokenType.IDENTIFIER;
        switch (word.toUpperCase()) {
            case "SELECT":
                type = TokenType.SELECT;
                break;
            case "FROM":
                type = TokenType.FROM;
                break;
            case "WHERE":
                type = TokenType.WHERE;
                break;
            case "AND":
                type = TokenType.AND;
                break;
            case "OR":
                type = TokenType.OR;
                break;
            case "GROUP":
                type = TokenType.GROUP;
                break;
            case "BY":
                type = TokenType.BY;
                break;
            case "ORDER":
                type = TokenType.ORDER;
                break;
            case "ASC":
                type = TokenType.ASC;
                break;
            case "DESC":
                type = TokenType.DESC;
                break;
            case "JOIN":
                type = TokenType.JOIN;
                break;

            case "ON":
                type = TokenType.ON;
                break;

            case "LEFT":
                type = TokenType.LEFT;
                break;

            case "RIGHT":
                type = TokenType.RIGHT;
                break;

            case "FULL":
                type = TokenType.FULL;
                break;

            case "INNER":
                type = TokenType.INNER;
                break;
        }
        return new Token(type, word, start);
    }
}