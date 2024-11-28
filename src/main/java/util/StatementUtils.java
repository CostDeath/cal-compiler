package util;

public abstract class StatementUtils {
    public static String genAssignStatement(String res, String left, String op, String right) {
        return String.format("%s = %s %s %s", res, left, op, right);
    }
    public static String genAssignStatement(String res, String left) {
        return String.format("%s = %s", res, left);
    }
    public static String genGetParamStatement(String name, int count) {
        return String.format("%s = getparam %d", name, count);
    }
}
