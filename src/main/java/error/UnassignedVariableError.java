package error;

import gen.calParser.ArgsContext;
import gen.calParser.CondContext;
import gen.calParser.ExpContext;

public class UnassignedVariableError extends GenericError {

    private static final String defaultFuncMessage =
            "Variable '%s' declared in function '%s' but never assigned a value.";

    private static final String defaultGlobalMessage =
            "Variable '%s' declared in global scope but never assigned a value.";
    private static final String defaultMessage =
            "Variable '%s' not assigned a value but called at: Line %s, char %s";


    public UnassignedVariableError(String varName, String funcName) {
        super("", String.format(defaultFuncMessage, varName, funcName));
    }

    public UnassignedVariableError(String varName) {
        super("", String.format(defaultGlobalMessage, varName));
    }

    public UnassignedVariableError(ArgsContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public UnassignedVariableError(ExpContext ctx) {
        super(String.format(defaultMessage,
                ctx.frag().IDENTIFIER().getText(),
                ctx.frag().IDENTIFIER().getSymbol().getLine(),
                ctx.frag().IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public UnassignedVariableError(CondContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
