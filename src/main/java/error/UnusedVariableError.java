package error;

public class UnusedVariableError extends GenericError {

    private static final String defaultFuncMessage =
            "Variable '%s' declared in function '%s' but never used.";
    private static final String defaultMessage =
            "Variable '%s' declared in global scope but never used.";


    public UnusedVariableError(String varName, String funcName) {
        super("", String.format(defaultFuncMessage, varName, funcName));
    }

    public UnusedVariableError(String varName) {
        super("", String.format(defaultMessage, varName));
    }
}
