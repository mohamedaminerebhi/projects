import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
    }

    public List<Token> tokenize() throws LexerException {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char currentChar = input.charAt(position);
            if (Character.isWhitespace(currentChar)) {
                position++;
            } else if (Character.isDigit(currentChar)) {
                tokens.add(readNumberorReal());
            } else if (Character.isLetter(currentChar)) {
                tokens.add(readIdentifierOrKeyword());
            } else {
                switch (currentChar) {
                    case ',':
                        tokens.add(new Token(TokenType.COMMA, ","));
                        break;
                    case '=':
                        tokens.add(assignmentOrEqual());
                        break;
                    case ';':
                        tokens.add(new Token(TokenType.SEMICOLON, ";"));
                        break;
                    case '(':
                        tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                        break;
                    case '{':
                        tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                        break;
                    case '}':
                        tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                        break;
                    case '+':
                        tokens.add(processPlusPlus());
                        break;
                    case '-':
                        tokens.add(processMinusMinus());
                        break;
                    case '/':
                        tokens.add(new Token(TokenType.DIVISION,"/"));
                        break;
                    case '>':
                    case '<':
                        tokens.add(new Token(TokenType.RELATIONAL_OPERATOR, String.valueOf(currentChar)));
                        break;
                    case '"':
                        tokens.add(readString());
                        break;
                    case ']':
                        tokens.add(new Token ((TokenType.RIGHT_BRACE),"]"));
                        break;
                    case '[':
                        tokens.add(new Token ((TokenType.LEFT_BRACE),"["));
                        break;
                    case '&':
                        tokens.add(new Token((TokenType.REFERENCE),"&"));
                    default:
                        throw new LexerException("Unexpected character: " + currentChar);
                }
                position++;
            }
        }
        return tokens;
    }

    private Token assignmentOrEqual() {
        if (peekNext() == '=') {
            position++;
            return new Token(TokenType.EQUAL, "==");
        }
        return new Token(TokenType.ASSIGNMENT_OPERATOR, "='");
    }

    private Token processPlusPlus() {
        if (peekNext() == '+') {
            position++;
            return new Token(TokenType.INCREMENT_OPERATOR, "++");
        }
        return new Token(TokenType.ADDITIVE_OPERATOR, "+");
    }

    private Token processMinusMinus() {
        if (peekNext() == '-') {
            position++;
            return new Token(TokenType.DECREMENT_OPERATOR, "--");
        }
        return new Token(TokenType.SUBTRACTIVE_OPERATOR, "-");
    }

    private char peekNext() {
        if (position + 1 < input.length()) {
            return input.charAt(position + 1);
        }
        return '\0';
    }

    private Token readString() throws LexerException {
        StringBuilder sb = new StringBuilder();
        position++; // Skip opening quote

        while (position < input.length() && input.charAt(position) != '"') {
            sb.append(input.charAt(position));
            position++;
        }
        if (position == input.length() || input.charAt(position) != '"') {
            throw new LexerException("Unclosed string literal");
        }
        position++; // Skip closing quote
        return new Token(TokenType.l, sb.toString());
    }

    private Token readNumberorReal() {
        StringBuilder sb = new StringBuilder();
        boolean hasDecimalPoint = false;
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            char currentChar = input.charAt(position);
            if (currentChar == '.') {
                if (hasDecimalPoint) {
                    break;
                }
                hasDecimalPoint = true;
            }
            sb.append(currentChar);
            position++;
        }
        return new Token(hasDecimalPoint ? TokenType.REAL : TokenType.nb, sb.toString());
    }

    private Token readIdentifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            sb.append(input.charAt(position));
            position++;
        }
        String identifier = sb.toString();
        // Check if the identifier is a keyword
        if (identifier.equals("int") || identifier.equals("void") || identifier.equals("main") ||
                identifier.equals("if") || identifier.equals("else") || identifier.equals("for") || identifier.equals("then")||
        identifier.equals("return") ||identifier.equals("string")||identifier.equals("float")||identifier.equals("bool")||
                identifier.equals("printf")||identifier.equals("scanf")||identifier.equals("array"))
        {
            return new Token(TokenType.KEYWORD, identifier);
        }
        return new Token(TokenType.id, identifier);
    }
}



