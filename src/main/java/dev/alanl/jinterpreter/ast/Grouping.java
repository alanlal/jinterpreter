package dev.alanl.jinterpreter.ast;

public class Grouping extends Expr{
    Expr expression;

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitGrouping(this);
    }
}
