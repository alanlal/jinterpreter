package dev.alanl.jinterpreter.ast;

import dev.alanl.jinterpreter.Token;

public class Binary extends Expr{
    Expr lexpr;
    Token operator;
    Expr rexpr;

    public Binary(Expr lexpr, Token operator, Expr rexpr) {
        this.lexpr = lexpr;
        this.operator = operator;
        this.rexpr = rexpr;
    }

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitBinary(this);
    }
}
