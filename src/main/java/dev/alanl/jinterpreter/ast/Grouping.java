package dev.alanl.jinterpreter.ast;

public class Grouping extends Expr{
    Expr expression;

    public Grouping(Expr expression) {
        this.expression = expression;
    }

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitGrouping(this);
    }
}
