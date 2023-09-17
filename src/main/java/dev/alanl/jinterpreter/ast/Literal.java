package dev.alanl.jinterpreter.ast;

public class Literal extends Expr {
    Object literal;

    public Literal(Object literal) {
        this.literal = literal;
    }

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}
