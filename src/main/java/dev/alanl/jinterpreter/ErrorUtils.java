package dev.alanl.jinterpreter;

public class ErrorUtils {

    public static class ParseError extends RuntimeException {
        Token token;
        String message;

        public ParseError(Token token, String message) {
            this.token=token;
            this.message = message;
        }
    }

    public static void error(String message, int line, int startPos, int currentPos) {
        System.out.printf("Error occurred at line %s, %s\n", line, message);
    }

    public static void error(Token token, String message) {
        System.out.printf("Error occurred at like %s, for token %s, %s", token.line, token.tokenType, message);
    }
}
