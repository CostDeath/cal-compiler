package error;

import gen.calParser.StmContext;

public class UnreachableCodeError extends GenericError {
    private static final String defaultMessage =
            "Unreachable code past a return statement on: Line %s, char %s";


    public UnreachableCodeError(StmContext ctx) {
        super("", String.format(defaultMessage,
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ));
    }
}
