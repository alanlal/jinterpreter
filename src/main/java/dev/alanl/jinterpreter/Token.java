package dev.alanl.jinterpreter;

import java.util.Objects;

public class Token {
    TokenType tokenType;
    public String lexeme;
    Object literal;
    int line;
    int startPos;
    int endPos;

    public Token(TokenType tokenType, String lexeme, Object literal, int line, int startPos, int endPos) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public Token(TokenType tokenType, String lexeme, Object literal) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = 0;
        this.startPos = 0;
        this.endPos = 0;
    }

    public static Token EOF(int pos, int line) {
        return new Token(TokenType.EOF, "", null, pos, pos, line);
    }

    @Override
    public String toString() {
        return "{" +
            "\"tokenType\":" + "\"" + tokenType + "\"" + "," +
            "\"lexeme\":" + "\"" + lexeme + "\"" + "," +
            "\"literal\":" + literal + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return tokenType == token.tokenType &&
               Objects.equals(lexeme, token.lexeme) &&
               Objects.equals(literal, token.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, lexeme, literal);
    }
}
