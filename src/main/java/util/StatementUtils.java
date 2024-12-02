package util;

public abstract class StatementUtils {
    public static String genAssignStatement(String res, String left, String op, String right) {
        return String.format("%s = %s %s %s", res, left, op, right);
    }

    public static String genAssignStatement(String res, String left) {
        return String.format("%s = %s", res, left);
    }

    public static String genAssignStatement(String left, String op, String right) {
        return String.format("%s %s %s", left, op, right);
    }

    public static String genGetParamStatement(String name, int count) {
        return String.format("%s = getparam %d", name, count);
    }

    public static String genSetParamStatement(String name) {
        return String.format("param %s", name);
    }

    public static String genCallStatement(String name, int count) {
        return String.format("call %s %d", name, count);
    }

    public static String genIfStatement(String cond, String func) {
        return String.format("if %s == true goto %s", cond, func);
    }

    public static String genElseStatement(String cond, String func) {
        return String.format("ifz %s == true goto %s", cond, func);
    }

    public static String genGotoStatement(String func) {
        return String.format("goto %s", func);
    }

    public static String genReturnStatement(String name) {
        return String.format("return %s", name);
    }

    public static String genReturnStatement() {
        return "return";
    }

    public static String genLabelStatement(String func) {
        return String.format("%s:", func);
    }
}
