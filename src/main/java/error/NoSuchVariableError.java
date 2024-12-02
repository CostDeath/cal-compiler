package error;

import gen.calParser.ArgsContext;
import gen.calParser.Assignment_stmContext;
import gen.calParser.CondContext;
import gen.calParser.ExpContext;

public class NoSuchVariableError extends GenericError {
    private static final String defaultMessage =
            "Variable '%s' not declared but called at: Line %s, char %s";


    public NoSuchVariableError(ArgsContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public NoSuchVariableError(Assignment_stmContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public NoSuchVariableError(ExpContext ctx) {
        super(String.format(defaultMessage,
                ctx.frag().IDENTIFIER().getText(),
                ctx.frag().IDENTIFIER().getSymbol().getLine(),
                ctx.frag().IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public NoSuchVariableError(CondContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
