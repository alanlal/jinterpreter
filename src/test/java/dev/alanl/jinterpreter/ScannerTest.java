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
                new Token(TokenType.NUMBER, "1", 1.0, 0, 0, 0),
                new Token(TokenType.PLUS, "+", null, 0, 0, 0),
                new Token(TokenType.NUMBER, "1", 1.0, 0, 0, 0),
                new Token(TokenType.EQUAL, "=", null, 0, 0, 0),
                new Token(TokenType.NUMBER, "2", 2.0, 0, 0, 0),
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
                new Token(TokenType.PRINT, "print", null, 0, 0, 0),
                new Token(TokenType.LEFT_PAREN, "(", null, 0, 0, 0),
                new Token(TokenType.STRING, "Hello World", "Hello World", 0, 0, 0),
                new Token(TokenType.RIGHT_PAREN, ")", null, 0, 0, 0),
                Token.EOF(0, 0)
        );

        Assertions.assertThat(tokens).containsExactlyElementsOf(tokenList);
    }
}