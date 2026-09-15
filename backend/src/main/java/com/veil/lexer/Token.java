package main.java.com.veil.lexer;

public class Token {
    private final int position;
    private final String value;
    private final TokenType type;

    public Token(TokenType type, String value, int position) {
        this.type = type;
        this.position = position;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token {" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", position=" + position +
                '}';
    }
}