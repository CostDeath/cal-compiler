package error;

public class MissingReturnStatementError extends GenericError {
    private static final String defaultMessage =
            "Function '%s' does not have an outer return statement.";


    public MissingReturnStatementError(String func) {
        super(String.format(defaultMessage, func), "");
    }
}
