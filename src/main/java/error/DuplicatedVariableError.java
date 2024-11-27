package error;

import gen.calParser;

public class DuplicatedVariableError extends GenericError {
    private static final String defaultMessage =
            "Duplicated variable '%s' at: Line %s, char %s";


    public DuplicatedVariableError(calParser.Var_declContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public DuplicatedVariableError(calParser.Const_declContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
