package error;

import gen.calParser.Func_callContext;
import util.TypeUtils;

import java.lang.reflect.Type;
import java.util.List;

public class MismatchedArgumentsError extends GenericError {
    private static final String defaultMessage =
            "Function '%s' requires arg types %s but was provided arg types %s at : Line %s, char %s";


    public MismatchedArgumentsError(Func_callContext ctx, List<Type> reqs, List<Type> given) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                reqs.stream().map(TypeUtils::getNameFromType).toList(),
                given.stream().map(TypeUtils::getNameFromType).toList(),
                ctx.args().getStart().getLine(),
                ctx.args().getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
