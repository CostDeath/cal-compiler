package error;

import gen.calParser;
import gen.calParser.ExpContext;

import java.lang.reflect.Type;

import static util.TypeUtils.getNameFromType;

public class IllegalOperationError extends GenericError {
    private static final String defaultMessage =
            "Expression of type '%s' used with variable '%s' of type '%s' at : Line %s, char %s";

    public IllegalOperationError(ExpContext ctx, Type given) {
        super(String.format(defaultMessage,
                getNameFromType(Integer.class),
                ctx.frag().IDENTIFIER().getText(),
                getNameFromType(given),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public IllegalOperationError(calParser.CondContext ctx, Type given) {
        super(String.format(defaultMessage,
                getNameFromType(Boolean.class),
                ctx.IDENTIFIER().getText(),
                getNameFromType(given),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
