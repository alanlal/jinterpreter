package dev.alanl.jinterpreter;

import dev.alanl.jinterpreter.ast.*;

import java.util.List;

import static dev.alanl.jinterpreter.TokenType.*;

public class Parser {
    List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr expression() {
        return equality();
    }

    private Expr equality() {
        Expr expr = comparison();
        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = term();
        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }


    private Expr factor() {
        Expr expr = unary();
        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr rightExpr = unary();
            expr = new Binary(expr, operator, rightExpr);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Unary(operator, right);
        }

        return primary();
    }

    private Expr primary() {
        if(match(TRUE)) return new Literal(TRUE);
        if (match(FALSE)) return new Literal(FALSE);
        if (match(NIL)) return new Literal(null);

        if (match(NUMBER, STRING)) {
            return new Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN,"cannot find right parenthesis");
            return new Grouping(expr);
        }

        return null;
    }

    private boolean match(TokenType... matchTokens) {
        for (TokenType matchTokenType : matchTokens) {
            if (check(matchTokenType)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private void consume(TokenType tokenType, String error) {
        if (check(tokenType)) {
            advance();
            return;
        }

        throw new IllegalArgumentException("error occured " + error + " token " + peek());
    }

    private void advance(){
        if (!isAtEnd()) {
            current++;
        }
    }

    private boolean check(TokenType tokenType) {
        return !isAtEnd() && peek().tokenType == tokenType;
    }

    private boolean isAtEnd(){
        return peek().tokenType == TokenType.EOF;
    }

    private Token peek() {
        return this.tokens.get(current);
    }

    private Token previous() {
        return this.tokens.get(current - 1);
    }
}
