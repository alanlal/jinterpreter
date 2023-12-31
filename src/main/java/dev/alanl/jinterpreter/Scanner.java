package dev.alanl.jinterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.alanl.jinterpreter.TokenType.*;

/**
 * The Scanner class is the first stage of our interpreter, responsible for converting
 * the source code into a list of tokens through lexical analysis. It processes the input
 * source code character by character, recognizing lexemes, associating token types, and
 * maintaining token metadata such as line numbers and positions. The resulting tokens
 * are essential for the subsequent stages of parsing and interpreting the code.
 */
public class Scanner {
    private final List<Token> tokens = new ArrayList<>();
    private String source = null;

    private int startPos = 0;
    private int currentPos = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            this.startPos = currentPos;
            scanToken();
        }

        tokens.add(Token.EOF(source.length() - 1, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);
            case '-' -> addToken(MINUS);
            case '+' -> addToken(PLUS);
            case ';' -> addToken(SEMICOLON);
            case '*' -> addToken(STAR);

            /*
            Certain characters need to be scanned as potential multi-character lexemes.
            For example, when encountering '!', it can be part of '!=' or just a single '!' token.
            Similarly, '=' can represent '==' or '=' independently, and '<' and '>' can form '<=', '>=',
            or be standalone '<' and '>'.
            This logic checks for these multi-character combinations and adds the appropriate tokens.
             */
            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '='-> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '<'-> addToken(match('=') ? LESS_EQUAL : LESS);
            case '>'-> addToken(match('=') ? GREATER_EQUAL : GREATER);

            /**
             * '/' can be part of a division operator or a comment line. To check that, the scanner checks
             * if '/' is followed by another '/', if so the rest of the characters till the EOL is ignored
             */
            case '/' -> {
                if (match('/')) {
                    while (peek()!='\n' && !isAtEnd()) {
                        advance();
                    }
                }else {
                    addToken(SLASH);
                }
            }

            case ' ','\r','\t' -> {}
            case '\n' -> {
                line++;
            }
            case '"' -> string();
            default -> {
                if (isDigit(c)) {
                    number();
                    return;
                }

                if (isAlpha(c)) {
                    identifier();
                    return;
                }

                ErrorUtils.error("Unexpected charecter", line, 0, 0);
            }
        }
    }
    
    private char peek() {
        return isAtEnd() ? '\0' : source.charAt(currentPos);
    }

    private char peekNext() {
        return isAtEnd() ? '\0' : source.charAt(currentPos + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(currentPos) != expected) return false;

        currentPos++;
        return true;
    }

    private char advance() {
        return source.charAt(currentPos++);
    }

    private boolean isAtEnd() {
        return currentPos >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' ;
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void addToken(TokenType tokenType) {
        addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String lexeme = source.substring(startPos, currentPos);
        Token token = new Token(tokenType, lexeme, literal, line, startPos, currentPos);
        this.tokens.add(token);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
            }
            advance();
        }


        if (isAtEnd()) {
            throw new IllegalStateException("Improper string termination");
        }

        advance();

        String literal = source.substring(startPos + 1, currentPos-1);
        addToken(STRING, literal);
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }

        addToken(NUMBER, Double.parseDouble(source.substring(startPos, currentPos)));
    }

    /**
     * The `identifier` method is responsible for recognizing and tokenizing identifiers in the source code.
     * An identifier is a sequence of letters, digits, and underscores that represents variable names, function names,
     * or user-defined symbols. This method performs a "maximal munch" operation, meaning it consumes as many
     * consecutive alphanumeric characters as possible to form a single identifier lexeme. It then checks if the
     * lexeme corresponds to a keyword, and if so, associates it with the appropriate keyword token; otherwise,
     * it is treated as a generic identifier token.
     */
    private void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }
        String lexeme = source.substring(startPos, currentPos);
        TokenType keywordToken = Keywords.getKeywordToken(lexeme);
        addToken(Objects.requireNonNullElse(keywordToken, IDENTIFIER));
    }

}
