package error;

import gen.calParser;
import gen.calParser.ExpContext;

import java.lang.reflect.Type;

import static util.TypeUtils.getNameFromType;

public class IllegalAssignmentError extends GenericError {
    private static final String defaultMessage =
            "Attemped re-assignment of constant '%s' at : Line %s, char %s";

    public IllegalAssignmentError(calParser.Assignment_stmContext ctx) {
        super(String.format(defaultMessage,
                ctx.IDENTIFIER().getText(),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
