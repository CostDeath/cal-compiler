package error;

import gen.calParser.Const_declContext;
import gen.calParser.ParamsContext;
import gen.calParser.Var_declContext;
import model.CalParam;

public class DuplicatedVariableError extends GenericError {
    private static final String defaultMessage =
            "Duplicated variable '%s' at: Line %s, char %s";


    public DuplicatedVariableError(Var_declContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public DuplicatedVariableError(Const_declContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.IDENTIFIER().getSymbol().getLine(),
                ctx.IDENTIFIER().getSymbol().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public DuplicatedVariableError(CalParam param, ParamsContext ctx) {
        super(String.format(defaultMessage,
                param.name(),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
