package dev.alanl.jinterpreter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    public void shouldDoScanning() {
        Scanner scanner = new Scanner("1+1=2");
        List<Token> tokens = scanner.scanTokens();
        List<Token> tokenList = List.of(
                new Token(TokenType.NUMBER, "1",1.0),
                new Token(TokenType.PLUS, "+",null),
                new Token(TokenType.NUMBER, "1",1.0),
                new Token(TokenType.EQUAL, "=",null),
                new Token(TokenType.NUMBER, "2",2.0),
                Token.EOF(0, 0)
        );

        Assertions.assertThat(tokens).containsExactlyElementsOf(tokenList);
    }

    @Test
    public void shouldDoScanning2() {
        Scanner scanner = new Scanner("""
                print("Hello World")
            """);
        List<Token> tokens = scanner.scanTokens();
        List<Token> tokenList = List.of(
                new Token(TokenType.PRINT, "print",null),
                new Token(TokenType.LEFT_PAREN, "(", null),
                new Token(TokenType.STRING, "\"Hello World\"", "Hello World"),
                new Token(TokenType.RIGHT_PAREN, ")", null),
                Token.EOF(0, 0)
        );

        Assertions.assertThat(tokens).containsExactlyElementsOf(tokenList);
    }

    @Test
    public void shouldParseFunction(){
        Scanner scanner = new Scanner("""
                fun add(a,b){
                    return a+b
                }
                """);

        List<Token> tokeList = List.of(
                new Token(TokenType.FUN, "fun", null),
                new Token(TokenType.IDENTIFIER, "add", null),
                new Token(TokenType.LEFT_PAREN, "(", null),
                new Token(TokenType.IDENTIFIER, "a", null),
                new Token(TokenType.COMMA, ",", null),
                new Token(TokenType.IDENTIFIER, "b", null),
                new Token(TokenType.RIGHT_PAREN, ")", null),
                new Token(TokenType.LEFT_BRACE, "{", null),
                new Token(TokenType.RETURN, "return", null),
                new Token(TokenType.IDENTIFIER, "a", null),
                new Token(TokenType.PLUS, "+", null),
                new Token(TokenType.IDENTIFIER, "b", null),
                new Token(TokenType.RIGHT_BRACE, "}", null),
                Token.EOF(0, 0)
        );

        List<Token> tokens = scanner.scanTokens();
        Assertions.assertThat(tokens).containsExactlyElementsOf(tokeList);
    }

    @Test
    public void shouldParseWhileLoop(){
        Scanner scanner = new Scanner("""
                while(true){
                    print("my name is alan")
                }
                """);

        List<Token> tokeList = List.of(
                new Token(TokenType.WHILE, "while", null),
                new Token(TokenType.LEFT_PAREN,"(",null),
                new Token(TokenType.TRUE,"true",null),
                new Token(TokenType.L,",",null),
                new Token(TokenType.IDENTIFIER,"b",null),
                new Token(TokenType.RIGHT_PAREN,"{",null),
                new Token(TokenType.RETURN,"return",null),
                new Token(TokenType.IDENTIFIER,"a",null),
                new Token(TokenType.PLUS,"+",null),
                new Token(TokenType.IDENTIFIER,"b",null),
                new Token(TokenType.LEFT_PAREN,"}",null)
        );
    }
}