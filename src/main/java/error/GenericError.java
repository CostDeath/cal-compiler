package error;

import org.antlr.v4.runtime.ParserRuleContext;

public class GenericError implements SemanticError {
    private final String message;
    private final String line;
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String defaultMessage = "A semantic error occurred on line %s";

    public GenericError(ParserRuleContext ctx) {
        message = String.format(defaultMessage, ctx.getStart().getLine());
        line = getErrorLine(ctx);
    }

    public GenericError(String message, String line) {
        this.message = message;
        this.line = line;
    }

    public static String getErrorLine(ParserRuleContext ctx) {
        StringBuilder lineBuilder = new StringBuilder();
        for(int i = 0; i < ctx.getChildCount(); i++) {
            lineBuilder.append(ctx.getChild(i).getText());
            lineBuilder.append(" ");
        }
        String line = lineBuilder.toString()
                .replace(" ;", ";")
                .trim()
                .concat("\n");
        return line.concat("^".repeat(line.length()-1));
    }

    @Override
    public String getMessage() {
        return RED +
                this.message +
                "\n" +
                YELLOW +
                this.line +
                RESET;
    }
}
