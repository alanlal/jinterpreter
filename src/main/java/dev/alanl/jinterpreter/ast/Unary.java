package dev.alanl.jinterpreter.ast;

import dev.alanl.jinterpreter.Token;

public class Unary extends Expr{
    Token operator;
    Expr expr;

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitUnary(this);
    }
}
