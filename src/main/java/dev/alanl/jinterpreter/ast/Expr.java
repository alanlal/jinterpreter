package dev.alanl.jinterpreter.ast;

public abstract class Expr {
    abstract <R> R accept(AstVisitor<R> visitor);
}