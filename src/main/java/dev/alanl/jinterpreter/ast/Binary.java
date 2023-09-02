package dev.alanl.jinterpreter.ast;

import dev.alanl.jinterpreter.Token;

public class Binary extends Expr{
    Expr lexpr;
    Token operator;
    Expr rexpr;

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitBinary(this);
    }
}
