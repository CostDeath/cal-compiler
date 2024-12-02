package error;

import gen.calParser.Func_callContext;

public class NoSuchFunctionError extends GenericError {
    private static final String defaultMessage =
            "Function '%s' not declared but called at: Line %s, char %s";


    public NoSuchFunctionError(Func_callContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
