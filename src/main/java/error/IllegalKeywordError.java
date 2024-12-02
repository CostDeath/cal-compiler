package error;

import gen.calParser.Return_stmContext;
import gen.calParser.Skip_stmContext;

public class IllegalKeywordError extends GenericError {
    private static final String defaultMessage =
            "%s keyword used %s : Line %s, char %s";

    public IllegalKeywordError(Skip_stmContext ctx) {
        super(String.format(defaultMessage,
                "Skip",
                "outside of a while loop",
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }

    public IllegalKeywordError(Return_stmContext ctx) {
        super(String.format(defaultMessage,
                "Return",
                "inside the main function",
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine()
        ), getErrorLine(ctx));
    }
}
