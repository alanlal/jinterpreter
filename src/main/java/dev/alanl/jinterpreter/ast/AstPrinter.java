package dev.alanl.jinterpreter.ast;

public class AstPrinter implements AstVisitor<String> {


    public String print(Expr expr) {
        return expr.accept(this);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }


    @Override
    public String visitBinary(Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.lexpr,expr.rexpr);
    }

    @Override
    public String visitUnary(Unary expr) {
        return parenthesize(expr.operator.lexeme,expr.expr);
    }

    @Override
    public String visitLiteral(Literal expr) {
        if (expr.literal == null) {
            return "nil";
        }
        return parenthesize(expr.literal.toString());
    }

    @Override
    public String visitGrouping(Grouping expr) {
        return parenthesize("grouping",expr.expression);
    }
}
