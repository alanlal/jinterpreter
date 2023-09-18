package dev.alanl.jinterpreter;

public class ErrorUtils {

    public static class Error extends RuntimeException {
        Token token;
        String message;

        public Error(Token token, String message) {
            this.token=token;
            this.message = message;
        }

    }

    public static void error(String message, int line, int startPos, int currentPos) {
        System.out.printf("Error occurred at line %s, %s\n", line, message);
    }
}
