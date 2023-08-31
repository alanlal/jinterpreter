package dev.alanl.jinterpreter;

import java.util.HashMap;

import static dev.alanl.jinterpreter.TokenType.*;

public class Keywords {
    private static final HashMap<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

    public static TokenType getKeywordToken(String keyword) {
        return keywords.get(keyword);
    }
}
