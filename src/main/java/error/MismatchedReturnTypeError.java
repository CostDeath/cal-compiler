package error;

import gen.calParser.Assignment_stmContext;
import gen.calParser.Func_callContext;
import gen.calParser.Return_stmContext;

import java.lang.reflect.Type;

import static util.TypeUtils.getNameFromType;

public class MismatchedReturnTypeError extends GenericError {

    private static final String defaultReturnMessage =
            "Function '%s' returns a value of type '%s' but was given type '%s' to return at : Line %s, char %s";
    private static final String defaultFuncMessage =
            "Function '%s' returns a value of type '%s' but was expected type '%s' at : Line %s, char %s";
    private static final String defaultMessage =
            "Expression returns a value of type '%s' but was expected type '%s' at : Line %s, char %s";

    public MismatchedReturnTypeError(Return_stmContext ctx, String funcName, Type req, Type given) {
        super(String.format(defaultReturnMessage,
                funcName,
                getNameFromType(req),
                getNameFromType(given),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public MismatchedReturnTypeError(Assignment_stmContext ctx, Func_callContext funCtx, Type req, Type given) {
        super(String.format(defaultFuncMessage,
                ctx.IDENTIFIER().getText(),
                getNameFromType(req),
                getNameFromType(given),
                funCtx.getStart().getLine(),
                funCtx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public MismatchedReturnTypeError(Assignment_stmContext ctx, Type req, Type given) {
        super(String.format(defaultMessage,
                getNameFromType(req),
                getNameFromType(given),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
