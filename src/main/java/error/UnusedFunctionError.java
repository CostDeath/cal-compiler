package error;

public class UnusedFunctionError extends GenericError {

    private static final String defaultMessage =
            "Function '%s' declared but never called.";


    public UnusedFunctionError(String funcName) {
        super("", String.format(defaultMessage, funcName));
    }
}
