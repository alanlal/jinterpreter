package dev.alanl.jinterpreter.ast;

public class Literal extends Expr {
    Object literal;

    @Override
    <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}
