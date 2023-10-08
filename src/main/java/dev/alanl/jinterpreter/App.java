package dev.alanl.jinterpreter;

import dev.alanl.jinterpreter.ast.AstPrinter;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>");
        while (scanner.hasNextLine()) {
            String expression = scanner.nextLine();
            System.out.println(expression);
            dev.alanl.jinterpreter.Scanner codeScanner = new dev.alanl.jinterpreter.Scanner(expression);
            List<Token> tokens = codeScanner.scanTokens();
            Parser parser = new Parser(tokens);
            AstPrinter astPrinter = new AstPrinter();
            System.out.println(astPrinter.print(parser.expression()));
            System.out.print(">>");
        }
    }
}
