package dev.alanl.jinterpreter;

import dev.alanl.jinterpreter.ast.*;

import java.util.List;

import static dev.alanl.jinterpreter.TokenType.*;

/**
 * The parser class takes the list of tokens generated from the Scanner and creates an abstract syntax tree
 * to represent the order of execution and the associativity of each operation. The parser uses recursive descent parsing
 * to traverse the grammar rules in the specified order and associativity to generate the AST
 *
 * following grammar is implemented so far in recursive descent way
 * expression     → equality ;
 * equality       → comparison ( ( "!=" | "==" ) comparison )* ;
 * comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
 * term           → factor ( ( "-" | "+" ) factor )* ;
 * factor         → unary ( ( "/" | "*" ) unary )* ;
 * unary          → ( "!" | "-" ) unary | primary ;
 * primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;
 */
public class Parser {
    List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr expression() {
        return equality();
    }

    /**
     * This method serves as a key component of a recursive descent parser, responsible for handling
     * the "equality" expression defined in the grammar.
     * In the grammar, the '*' operator symbolizes "0 or more occurrences," while the associated
     * while loop iterates as long as matching tokens are found.
     * The primary objective of this method is to parse and construct an Abstract Syntax Tree (AST)
     * representing equality expressions, such as '==' and '!=' comparisons. A similar approach
     * is followed for subsequent methods like 'comparison()', 'term()', 'factor()', and so on.
     */

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

        // as part of grouping, if we are matching a (, then we must match ) as well
        // or else it is an error
        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN,"cannot find right parenthesis");
            return new Grouping(expr);
        }

        throw error(peek(), "expression expected");
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

        //will be captured by "panic mode" error recovery
        throw new ErrorUtils.ParseError(peek(), error);
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

    private ErrorUtils.ParseError error(Token token, String message) {
        ErrorUtils.error(token, message);
        return new ErrorUtils.ParseError(token, message);
    }
}
