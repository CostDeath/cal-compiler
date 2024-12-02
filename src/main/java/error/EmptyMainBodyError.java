package error;

public class EmptyMainBodyError extends GenericError {
    private static final String defaultMessage =
            "Main function does not include any statements.";


    public EmptyMainBodyError() {
        super("", defaultMessage);
    }
}
