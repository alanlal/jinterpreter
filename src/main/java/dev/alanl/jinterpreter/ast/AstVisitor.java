package dev.alanl.jinterpreter.ast;

public interface AstVisitor<T> {
    T visitBinary(Binary expr);
    T visitUnary(Unary expr);
    T visitLiteral(Literal expr);
    T visitGrouping(Grouping expr);
}
