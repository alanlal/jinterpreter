package dev.alanl.jinterpreter;

import dev.alanl.jinterpreter.ast.AstPrinter;
import dev.alanl.jinterpreter.ast.Expr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void shouldParse(){
        String source = """
                  1+2=xx
                """;
        Scanner scanner = new Scanner(source);
        Parser parser = new Parser(scanner.scanTokens());
        Expr expression = parser.expression();
        AstPrinter astPrinter = new AstPrinter();
        String print = astPrinter.print(expression);
        System.out.println(print);
    }
}